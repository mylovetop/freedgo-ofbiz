package com.qihua.ofbiz.systemMgr;

import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.condition.*;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 15/12/23.
 */
public class RoleServices {
    
    public static final String module = RoleServices.class.getName();
    public static final String resource = "SystemMgrUiLabels";
    public static final String resourceError = "SystemMgrErrorUiLabels";
    
    /**
     * 查询所有的角色
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryAllSecurityGroup(DispatchContext dcx, Map<String, ? extends Object> context) {
        Delegator delegator = dcx.getDelegator();
        String productStoreId = (String) context.get("productStoreId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        try {
//            List<GenericValue> roles = delegator.findByAnd("SecurityGroup", (Object[]) null);
            List<GenericValue> roles=FastList.newInstance();
            if(UtilValidate.isNotEmpty(productStoreId)) {
                roles = delegator.findByAnd("SecurityGroup", UtilMisc.toMap("productStoreId", productStoreId));
            }
            result.put("securityGroups", roles);
            
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 分页查询出角色，条件为角色标示、角色名称
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> findRoles(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String groupId = (String) context.get("groupId");
        String name = (String) context.get("name");
        String description = (String) context.get("description");
        String productStoreId=(String) context.get("productStoreId"); // 店铺编码
        Delegator delegator = dcx.getDelegator();
        
        String userLoginId = (String) context.get("userLoginId");
        Locale locale = (Locale) context.get("locale");
        String lookupFlag = (String) context.get("lookupFlag");
        if (lookupFlag == null) {
            lookupFlag = "Y";
        }
        String orderFiled = (String) context.get("ORDER_FILED");
        String orderFiledBy = (String) context.get("ORDER_BY");
        
        result.put("orderFiled", orderFiled == null ? "" : orderFiled);
        result.put("orderBy", orderFiledBy == null ? "" : orderFiledBy);
        
        
        List<GenericValue> roleList = FastList.newInstance();
        int roleListSize = 0;
        int lowIndex = 0;
        int highIndex = 0;
        // set the page parameters
        int viewIndex = 0;
        try {
//            viewIndex = (Integer) context.get("viewIndex");
            viewIndex = Integer.parseInt((String) context.get("VIEW_INDEX"));
        } catch (Exception e) {
            viewIndex = 0;
        }
        result.put("viewIndex", Integer.valueOf(viewIndex));
        
        int viewSize = 20;
        try {
//            viewSize = (Integer) context.get("viewSize");
            viewSize = Integer.parseInt((String) context.get("VIEW_SIZE"));
        } catch (Exception e) {
            viewSize = 20;
        }
        result.put("viewSize", Integer.valueOf(viewSize));
        
        List<String> fieldsToSelect = FastList.newInstance();
        fieldsToSelect.add("groupId");
        fieldsToSelect.add("description");
        fieldsToSelect.add("name");
        List<String> orderBy = FastList.newInstance();
        if (UtilValidate.isNotEmpty(orderFiled)) {
            if ("roleId".equals(orderFiled)) {
                orderFiled = "groupId";
            }
            orderBy.add(orderFiled + " " + orderFiledBy);
        }
        // blank param list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;
        String paramList = "";
        if (UtilValidate.isNotEmpty(groupId)) {
            paramList = paramList + "&groupId=" + groupId;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("groupId"), EntityOperator.LIKE, EntityFunction.UPPER("%" + groupId + "%")));
        }
        if (UtilValidate.isNotEmpty(name)) {
            paramList = paramList + "&name=" + groupId;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("name"), EntityOperator.LIKE, EntityFunction.UPPER("%" + name + "%")));
        }
        if (UtilValidate.isNotEmpty(description)) {
            paramList = paramList + "&description=" + groupId;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("description"), EntityOperator.LIKE, EntityFunction.UPPER("%" + description + "%")));
        }
        // 店铺信息
        if (UtilValidate.isNotEmpty(productStoreId)) {
            paramList = paramList + "&productStoreId=" + groupId;
            andExprs.add(EntityCondition.makeCondition("productStoreId", productStoreId));
        }
        List<GenericValue> securityGroups = null;
        // build the main condition
        if (andExprs.size() > 0) {
            mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        }
        
        if ("Y".equals(lookupFlag)) {
            try {
                // get the indexes for the partial list
                // get the indexes for the partial list
                lowIndex = viewIndex * viewSize + 1;
                highIndex = (viewIndex + 1) * viewSize;
                
                // set distinct on so we only get one row per order
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, highIndex, true);
                // using list iterator
                EntityListIterator pli = delegator.find("SecurityGroup", mainCond, null, UtilMisc.makeSetWritable(fieldsToSelect), orderBy, findOpts);
                
                // get the partial list for this page
                roleList = pli.getPartialList(lowIndex, viewSize);
                
                // attempt to get the full size
                roleListSize = pli.getResultsSizeAfterPartialList();
                if (highIndex > roleListSize) {
                    highIndex = roleListSize;
                }
                
                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                String errMsg = "Failure in party find operation, rolling back transaction: " + e.toString();
                Debug.logError(e, errMsg, module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "PartyLookupPartyError",
                        UtilMisc.toMap("errMessage", e.toString()), locale));
            }
        } else {
            roleListSize = 0;
        }
        result.put("roleList", roleList);
        result.put("groupId", groupId);
        result.put("name", name);
        result.put("description", description);
        result.put("roleListSize", Integer.valueOf(roleListSize));
        result.put("paramList", paramList);
        result.put("highIndex", Integer.valueOf(highIndex));
        result.put("lowIndex", Integer.valueOf(lowIndex));
        
        return result;
        
    }
    
    /**
     * 查询功能权限树，树的根节点为-1 ，查询每个父节点对应子节点需要使用递归方法
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> findPermissionTree(DispatchContext dcx, Map<String, ? extends Object> context) {
        List tree = FastList.newInstance();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, String> oriMap = new HashMap<String, String>();

        String isInner = (String) context.get("isInner");
        List<EntityExpr> menuTypes=FastList.newInstance();

        oriMap.put("id", "-1");
        oriMap.put("name", "权限根");
        oriMap.put("pId", "");
        tree.add(oriMap);
        try {
            List<EntityCondition> andExprs = FastList.newInstance();
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("parentPermissionId"), EntityOperator.EQUALS, "-1"));
            if(UtilValidate.isNotEmpty(isInner)){
                if("Y".equals(isInner)){
                    menuTypes = UtilMisc.toList(EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "A"),
                            EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "P"));


                }else if("N".equals(isInner)){
                    menuTypes = UtilMisc.toList(EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "A"),
                            EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "B"));
                }else{

                }
                if(UtilValidate.isNotEmpty(menuTypes)){
                    andExprs.add(EntityCondition.makeCondition(menuTypes, EntityOperator.OR));
                }
            }
            EntityCondition mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
            List<GenericValue> securityPermissions = delegator.findList("SecurityPermission", mainCond, null, null, null, false);
            if (UtilValidate.isNotEmpty(securityPermissions)) {
                for (GenericValue permission : securityPermissions) {
                    String permissionId = (String) permission.get("permissionId");
                    String name = (String) permission.get("name");
                    String parentPermissionId = (String) permission.get("parentPermissionId");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", permissionId);
                    map.put("name", name);
                    map.put("pId", parentPermissionId);
                    tree.add(map);
//                    permissionRecursive(tree, delegator, permissionId);
                    permissionRecursive(tree, delegator, permissionId,isInner);
                }
            }
            result.put("permissionTree", tree);
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        
        return result;
        //
    }
    
    /**
     * 查询功能权限树，树的根节点为-1 ，查询每个父节点对应子节点需要使用递归方法
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> findPermissionsTree1(DispatchContext dcx, Map<String, ? extends Object> context) {
        List tree = FastList.newInstance();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        
        String permissions = (String) context.get("permissions");
        List<String> permissionList = StringUtil.split(permissions, ",");
        try {
            Map<String, String> oriMap = new HashMap<String, String>();
            oriMap.put("id", "-1");
            oriMap.put("name", "权限根");
            oriMap.put("pId", "");
            tree.add(oriMap);
            
            for (int i = 0; i < permissionList.size(); i++) {
                String pId = permissionList.get(i);
                GenericValue sp = delegator.findByPrimaryKey("SecurityPermission", UtilMisc.toMap("permissionId", pId));
                
                String spId = (String) sp.get("permissionId");
                String spName = (String) sp.get("description");
                String pSpId = (String) sp.get("parentPermissionId");
                Map<String, String> pmap = new HashMap<String, String>();
                pmap.put("id", spId);
                pmap.put("name", spName);
                pmap.put("pId", pSpId);
                tree.add(pmap);
                
                
                List<EntityCondition> andExprs = FastList.newInstance();
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("parentPermissionId"), EntityOperator.EQUALS, pId));
                EntityCondition mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
                List<GenericValue> securityPermissions = delegator.findList("SecurityPermission", mainCond, null, null, null, false);
                if (UtilValidate.isNotEmpty(securityPermissions)) {
                    for (GenericValue permission : securityPermissions) {
                        String permissionId = (String) permission.get("permissionId");
                        String name = (String) permission.get("description");
                        String parentPermissionId = (String) permission.get("parentPermissionId");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", permissionId);
                        map.put("name", name);
                        map.put("pId", parentPermissionId);
                        tree.add(map);
                        permissionRecursive(tree, delegator, permissionId);
                    }
                }
            }
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        result.put("permissionTree", tree);
        return result;
        //
    }
    
    private static void permissionRecursive(List<Map<String, String>> list, Delegator delegator, String pPermissionId) {
        try {
            List<EntityCondition> andExprs = FastList.newInstance();
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("parentPermissionId"), EntityOperator.EQUALS, pPermissionId));
            EntityCondition mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
            List<GenericValue> securityPermissions = delegator.findList("SecurityPermission", mainCond, null, null, null, false);
            if (UtilValidate.isNotEmpty(securityPermissions)) {
                for (GenericValue permission : securityPermissions) {
                    String permissionId = (String) permission.get("permissionId");
                    String name = (String) permission.get("name");
                    String parentPermissionId = (String) permission.get("parentPermissionId");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", permissionId);
                    map.put("name", name);
                    map.put("pId", parentPermissionId);
                    list.add(map);
                    permissionRecursive(list, delegator, permissionId);
                }
            }
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
    }


    private static void permissionRecursive(List<Map<String, String>> list, Delegator delegator, String pPermissionId,String isInner) {
        try {
            List<EntityCondition> andExprs = FastList.newInstance();
            List<EntityExpr> menuTypes=FastList.newInstance();
            if(UtilValidate.isNotEmpty(isInner)){
                if("Y".equals(isInner)){
                    menuTypes = UtilMisc.toList(EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "A"),
                            EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "P"));


                }else if("N".equals(isInner)){
                    menuTypes = UtilMisc.toList(EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "A"),
                            EntityCondition.makeCondition("menuType", EntityOperator.EQUALS, "B"));
                }else{

                }
                if(UtilValidate.isNotEmpty(menuTypes)){
                    andExprs.add(EntityCondition.makeCondition(menuTypes, EntityOperator.OR));
                }
            }
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("parentPermissionId"), EntityOperator.EQUALS, pPermissionId));
            EntityCondition mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
            List<GenericValue> securityPermissions = delegator.findList("SecurityPermission", mainCond, null, null, null, false);
            if (UtilValidate.isNotEmpty(securityPermissions)) {
                for (GenericValue permission : securityPermissions) {
                    String permissionId = (String) permission.get("permissionId");
                    String name = (String) permission.get("name");
                    String parentPermissionId = (String) permission.get("parentPermissionId");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", permissionId);
                    map.put("name", name);
                    map.put("pId", parentPermissionId);
                    list.add(map);
                    permissionRecursive(list, delegator, permissionId,isInner);
                }
            }
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
    }
    
    /**
     * 增加角色及角色对应功能权限
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> addRole(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String permissionIds = (String) context.get("permissionIds");
        
        String name = (String) context.get("name");
        String description = (String) context.get("description");
        String groupId = (String) context.get("groupId");
        // 店铺编码
        String productStoreId = (String) context.get("productStoreId");
        List<GenericValue> saveObj = FastList.newInstance();
        saveObj.add(delegator.makeValue("SecurityGroup", UtilMisc.toMap("groupId", groupId, "name", name, "description", description,"productStoreId",productStoreId)));
        
        for (String permissionId : StringUtil.split(permissionIds, ",")) {
            if (!"-1".equals(permissionId)) {
                saveObj.add(delegator.makeValue("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId, "permissionId", permissionId)));
            }
        }
        
        
        try {
            //增加默认有的权限
            Object pIds = UtilProperties.getPropertyValue("system.properties", "default_permission");
            Map<String, Object> permissionsTree = findPermissionsTree(dcx, UtilMisc.toMap("permissions", pIds));
            List tree = (List) permissionsTree.get("permissionTree");
            if (UtilValidate.isNotEmpty(tree)) {
                for (int i = 0; i < tree.size(); i++) {
                    Map map = (Map) tree.get(i);
                    String perId = (String) map.get("id");
                    if (!"-1".equals(perId)) {
                        saveObj.add(delegator.makeValue("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId, "permissionId", perId)));
                    }
                }
            }
            List<GenericValue> newObjs = FastList.newInstance();
            newObjs.addAll(saveObj);
            delegator.storeAll(newObjs);
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        
        
        return result;
    }
    
    /**
     * 修改角色及角色对应功能权限
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> updateRole(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String permissionIds = (String) context.get("permissionIds");
        String name = (String) context.get("name");
        String description = (String) context.get("description");
        String groupId = (String) context.get("groupId");
        // 店铺编码
        String productStoreId = (String) context.get("productStoreId");
        try {
            delegator.removeByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId));
            List<GenericValue> saveObj = FastList.newInstance();
            saveObj.add(delegator.makeValue("SecurityGroup", UtilMisc.toMap("groupId", groupId, "name", name, "description", description,"productStoreId",productStoreId)));
            
            for (String permissionId : StringUtil.split(permissionIds, ",")) {
                if (!"-1".equals(permissionId)) {
                    saveObj.add(delegator.makeValue("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId, "permissionId", permissionId)));
                }
            }
            
            //增加默认有的权限
            Object pIds = UtilProperties.getPropertyValue("system.properties", "default_permission");
            Map<String, Object> permissionsTree = findPermissionsTree(dcx, UtilMisc.toMap("permissions", pIds));
            List tree = (List) permissionsTree.get("permissionTree");
            if (UtilValidate.isNotEmpty(tree)) {
                for (int i = 0; i < tree.size(); i++) {
                    Map map = (Map) tree.get(i);
                    String perId = (String) map.get("id");
                    if (!"-1".equals(perId)) {
                        saveObj.add(delegator.makeValue("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId, "permissionId", perId)));
                    }
                }
            }
            List<GenericValue> newObjs = FastList.newInstance();
            newObjs.addAll(saveObj);
            delegator.storeAll(newObjs);
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除角色及角色与权限对应关系
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> deleteRole(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        String groupId = (String) context.get("groupId");
        try {
            delegator.removeByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId));
            delegator.removeByAnd("SecurityGroup", UtilMisc.toMap("groupId", groupId));
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        return result;
        //
    }
    
    /**
     * 删除多个角色及角色与权限对应关系
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> deleteRoles(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        try {
            Delegator delegator = dcx.getDelegator();
            String groupIds = (String) context.get("groupIds");
            
            if (UtilValidate.isNotEmpty(groupIds)) {
                for (String groupId : StringUtil.split(groupIds, ",")) {
                    delegator.removeByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId));
                    delegator.removeByAnd("SecurityGroup", UtilMisc.toMap("groupId", groupId));
                }
            }
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        return result;
        //
    }
    
    
    /**
     * 查询角色详细信息机角色对应的功能权限关系
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryRoleDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String groupId = (String) context.get("groupId");
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        
        try {
            GenericValue securityGroup = delegator.findByPrimaryKey("SecurityGroup", UtilMisc.toMap("groupId", groupId));
            if (UtilValidate.isNotEmpty(securityGroup)) {
                List<GenericValue> securityGroupPermissions = delegator.findByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", groupId));
                result.put("securityGroupPermissions", securityGroupPermissions);
            }
            
            result.put("securityGroup", securityGroup);
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        return result;
    }
    
    /**
     * 查询功能权限树，树的根节点为-1 ，查询每个父节点对应子节点需要使用递归方法
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> findPermissionsTree(DispatchContext dcx, Map<String, ? extends Object> context) {
        List tree = FastList.newInstance();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        
        String permissions = (String) context.get("permissions");
        List<String> permissionList = StringUtil.split(permissions, ",");
        try {
            Map<String, String> oriMap = new HashMap<String, String>();
            oriMap.put("id", "-1");
            oriMap.put("name", "权限根");
            oriMap.put("pId", "");
            tree.add(oriMap);
            
            for (int i = 0; i < permissionList.size(); i++) {
                String pId = permissionList.get(i);
                GenericValue sp = delegator.findByPrimaryKey("SecurityPermission", UtilMisc.toMap("permissionId", pId));
                System.out.println("pId = " + pId);
                String spId = (String) sp.get("permissionId");
                String spName = (String) sp.get("description");
                String pSpId = (String) sp.get("parentPermissionId");
                Map<String, String> pmap = new HashMap<String, String>();
                pmap.put("id", spId);
                pmap.put("name", spName);
                pmap.put("pId", pSpId);
                tree.add(pmap);
                
                
                List<EntityCondition> andExprs = FastList.newInstance();
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("parentPermissionId"), EntityOperator.EQUALS, pId));
                EntityCondition mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
                List<GenericValue> securityPermissions = delegator.findList("SecurityPermission", mainCond, null, null, null, false);
                if (UtilValidate.isNotEmpty(securityPermissions)) {
                    for (GenericValue permission : securityPermissions) {
                        String permissionId = (String) permission.get("permissionId");
                        String name = (String) permission.get("description");
                        String parentPermissionId = (String) permission.get("parentPermissionId");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", permissionId);
                        map.put("name", name);
                        map.put("pId", parentPermissionId);
                        tree.add(map);
                        permissionRecursive(tree, delegator, permissionId);
                    }
                }
            }
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        result.put("permissionTree", tree);
        return result;
        //
    }


    /**
     * 删除角色验证
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> chkRoles(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        try {
            Delegator delegator = dcx.getDelegator();
            String groupIds = (String) context.get("groupIds");
            StringBuffer errIds= new StringBuffer();
            String errGroupIds="";
            String chkFlg="OK";

            if (UtilValidate.isNotEmpty(groupIds)) {
                for (String groupId : StringUtil.split(groupIds, ",")) {
                    List<GenericValue> UserLoginSecurityGroupInfos=delegator.findByAnd("UserLoginSecurityGroup",UtilMisc.toMap("groupId",groupId));
                    if(UtilValidate.isNotEmpty(UserLoginSecurityGroupInfos)){
                        if(UtilValidate.isNotEmpty(errIds.toString())){
                            errIds.append(",");
                            errIds.append(groupId);
                        }else{
                            errIds.append(groupId);
                        }
                    }
                }
            }

            errGroupIds=errIds.toString();
            if(UtilValidate.isNotEmpty(errGroupIds)){
                chkFlg="NG";
            }

            result.put("errGroupIds",errGroupIds);
            result.put("chkFlg",chkFlg);
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }
        return result;
        //
    }
}
