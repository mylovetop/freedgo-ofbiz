<#--
左侧定义应用菜单
-->
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#if (externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#assign ofbizServerName = application.getAttribute("_serverId")?default("default-server")>
<#assign contextPath = request.getContextPath()>
<#assign displayApps = Static["org.ofbiz.base.component.ComponentConfig"].getAppBarWebInfos(ofbizServerName, "main")>
<#assign displaySecondaryApps = Static["org.ofbiz.base.component.ComponentConfig"].getAppBarWebInfos(ofbizServerName, "secondary")>
<#-- 设置不显示的一级菜单  -->
<#assign nodisplayAppsNames = ["ar","ap","account","practice","ebay","example","exampleext","googlebase","oagis","ebaystore"]>

<#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
<#if appModelMenu.getModelMenuItemByName(headerItem)?exists>
    <#if headerItem!="main">
        <#assign show_last_menu = true>
    </#if>
</#if>
<#if parameters.portalPageId?exists && !appModelMenu.getModelMenuItemByName(headerItem)?exists>
    <#assign show_last_menu = true>
</#if>
<#--<div id="navigation" <#if show_last_menu?exists>class="menu_selected"</#if>>-->
<#if userLogin?has_content>
    <#if layoutSettings.topLines?has_content>
        <#list layoutSettings.topLines as topLine>
            <#if topLine.text?exists>
                <#assign personName = topLine.text/>
                <#assign profileUrl = topLine.url />
                <#assign profileText = topLine.urlText />
            </#if>
        </#list>
    <#else>
        <#assign personName = userLogin.userLoginId />
    </#if>
<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                     class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><#if person?exists>${person.name?if_exists}</#if></p>
                <a><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
    <#-- <form action="#" method="get" class="sidebar-form">
         <div class="input-group">
             <input type="text" name="q" class="form-control" placeholder="Search...">
           <span class="input-group-btn">
             <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
             </button>
           </span>
         </div>
     </form>-->
        <ul class="sidebar-menu">
            <li class="header">主菜单</li>

        <#-- Primary Applications -->
          <#assign  pageSize = 0 >
            <#assign startDate = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >
          <#list displayApps as display>
              <#assign nowTimestampString = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >
              <#if pageSize < 15>
                  <#assign pageSize = pageSize + 1>
                  <#assign thisApp = display.getContextRoot()>

                  <#assign permission = true>
                  <#assign selected = false>
                  <#assign permissions = display.getBasePermission()>
                  <#assign permissionStartTimestamp = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >
                  <#list permissions as perm>
                      <#if (perm != "NONE" && !security.hasEntityPermission(perm, "_VIEW", session) && !authz.hasPermission(session, perm, requestParameters))>
                      <#-- User must have ALL permissions in the base-permission list -->
                          <#assign permission = false>
                      </#if>
                  </#list>
                  <#--<#assign permissionTimestamp = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >-->
                  <#--<#assign permissionRunTime = Static["org.ofbiz.base.util.UtilDateTime"].getInterval(permissionStartTimestamp,permissionTimestamp) >-->
                  <#--${display.title}权限执行:${permissionRunTime}-->
                  <#if permission == true>
                      <#if thisApp == contextPath || contextPath + "/" == thisApp>
                          <#assign selected = true>
                      </#if>
                      <#assign thisApp = StringUtil.wrapString(thisApp)>
                      <#assign thisURL = thisApp>
                      <#if thisApp != "/">
                          <#assign thisURL = thisURL + "/control/main">
                      </#if>
                      <#if layoutSettings.suppressTab?exists && display.name == layoutSettings.suppressTab>
                  <!-- do not display this component-->
                      <#else>
                  <li class="treeview<#if selected>   active</#if>">
                    <#if uiLabelMap?exists>
                        <#assign desc = uiLabelMap[display.description]/>
                        <#assign title = uiLabelMap[display.title]/>
                    <#else>
                        <#assign desc = display.description/>
                        <#assign title = display.title/>
                    </#if>
                    <#assign appUrl = thisApp?substring(1)/>
                    <#assign applicationMenuLocation = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLocation(appUrl)?default("")/>
                    <#assign applicationMenuName = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuName(appUrl)?default("")/>
                    <#assign uiLabels = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLabel(appUrl)?default("")/>
                    <#assign uistyle = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuStyle(appUrl)?default("")/>
                      <a href="#" title="${desc}">
                          <i class="${uistyle}"></i><span>${title}</span>
                          <span class="fa fa-angle-left pull-right"></span>
                      </a>

                    <#if applicationMenuLocation!="" && applicationMenuName!="">
                        <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                        <ul class="treeview-menu">
                          <#assign menuItemList = appModelMenu.getMenuItemList()>
                          <#if menuItemList?exists>
                              <#list menuItemList as menuItem>
                                  <#assign passed = true />

                                  <#if (!menuItem.shouldBeRendered(context))>
                                      <#assign  passed = false/>
                                  </#if>

                                  <#if passed>
                                      <#assign hasSub = menuItem.getMenuItemList()?has_content/>
                                      <#assign hasLink = menuItem.getLink()?exists/>
                                      <#assign active = false/>
                                      <#assign subActive = false/>
                                      <#if menuItem.getLink()?exists>
                                          <#assign itemLink = menuItem.getLink()>
                                          <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+itemLink.getTarget(context))>
                                          <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                          <#if (currentURI = requestURI)>
                                              <#assign active = true/>
                                          <#elseif (("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index ==0)>
                                              <#assign active = true />
                                          </#if>
                                      </#if>
                                      <#if hasSub>
                                          <#assign subMenuItems = menuItem.getMenuItemList()>
                                          <#list subMenuItems as subMenuItem>
                                              <#assign subItemLink = subMenuItem.getLink()>
                                              <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                              <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                              <#if (currentURI == requestURI )>
                                                  <#assign subActive = true/>
                                              <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                                  <#assign subActive = true/>
                                              </#if>
                                          </#list>
                                      </#if>

                                  <li<#if hasSub && (active ||subActive)> class="tree-view active"<#elseif (hasSub)> class="tree-view"<#elseif (active ||subActive)>
                                                                          class="active"</#if>>
                                    <#assign  itemTitle = menuItem.getTitle(context)>
                                    <#list uiLabels as uiLabel>
                                        <#assign itemTitle = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, itemTitle, locale)/>
                                    </#list>
                                    <#if hasLink>
                                        <#assign itemLink = menuItem.getLink()>
                                        <#assign commonDisplaying = itemLink.getText(context)>
                                        <#list uiLabels as uiLabel>
                                            <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                        </#list>
                                        <#if !commonDisplaying?starts_with("&#")>
                                            <#assign commonDisplaying = itemTitle>
                                        </#if>
                                        <#if itemLink.getParameterMap(context)?has_content>
                                            <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                        <#else >
                                            <#assign externalKeyParam1 = externalKeyParam/>
                                        </#if>
                                        <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}">
                                            <i class="fa fa-circle-o"></i>${commonDisplaying}<#if hasSub><span class="fa fa-angle-left pull-right"></span></#if></a>
                                    <#else>
                                        <a href="#"><i class="fa fa-circle-o"></i>${itemTitle}<#if hasSub><span
                                                class="fa fa-angle-left pull-right"></span></#if></a>
                                    </#if>
                                    <#if hasSub>
                                        <ul class="treeview-menu">
                                          <#assign subMenuItems = menuItem.getMenuItemList()>
                                          <#list subMenuItems as subMenuItem>
                                              <#--<#assign subMenuItemStart = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >-->
                                              <#if (!subMenuItem.shouldBeRendered(context))>
                                                  <#assign  passed = false/>
                                              </#if>
                                              <#--<#assign subMenuItemEnd = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >-->
                                              <#--<#assign subMenuItemRunTime = Static["org.ofbiz.base.util.UtilDateTime"].getInterval(subMenuItemStart,subMenuItemEnd) >-->
                                              <#--${subMenuItem.getLink()?default("")}权限执行:${subMenuItemRunTime}-->
                                              <#if passed>
                                                  <#assign subItemLink = subMenuItem.getLink()>
                                                  <#assign subCommonDisplaying = subItemLink.getText(context)>
                                                  <#list uiLabels as uiLabel>
                                                      <#assign subCommonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, subCommonDisplaying, locale)/>
                                                  </#list>
                                                  <#if !subCommonDisplaying?starts_with("&#")>
                                                      <#assign subCommonDisplaying = subMenuItem.getTitle(context)>
                                                  </#if>
                                                  <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                                  <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                  <#if (currentURI = requestURI )>
                                              <li class="active">
                                                  <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                              <li class="active">
                                                  <#else>
                                              <li>
                                                  </#if>
                                                  <#if subItemLink.getParameterMap(context)?has_content>
                                                      <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                                  <#else >
                                                      <#assign externalKeyParam1 = externalKeyParam/>
                                                  </#if>
                                                <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a></li>
                                              </#if>
                                          </#list>
                                        </ul>
                                    </#if>
                                  </li>

                                  </#if>
                              </#list>
                          </#if>
                        </ul>
                    </#if>
                  </li>
                      </#if>
                  </#if>
              </#if>
              <#--<#assign nowTimestampStringEnd = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >-->
              <#--<#assign runTime = Static["org.ofbiz.base.util.UtilDateTime"].getInterval(nowTimestampString,nowTimestampStringEnd) >-->
              <#--${display.title}:${runTime}-->
          </#list>
        <#--<#assign endDate = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >-->
            <#--<#assign runAllTime = Static["org.ofbiz.base.util.UtilDateTime"].getInterval(startDate,endDate) >-->
            <#--整个树： ${runAllTime}-->
        <#-- Secondary Applications -->
        <#-- <#list displaySecondaryApps as display>
           <#assign thisApp = display.getContextRoot()>
             <#assign permission = true>
           <#assign selected = false>
           <#assign permissions = display.getBasePermission()>

           <#list permissions as perm>

             <#if (perm != "NONE" && !security.hasEntityPermission(perm, "_VIEW", session) && !authz.hasPermission(session, perm, requestParameters))>

             &lt;#&ndash; User must have ALL permissions in the base-permission list &ndash;&gt;
               <#assign permission = false>
             </#if>
           </#list>
           <#if permission == true>
             <#if thisApp == contextPath || contextPath + "/" == thisApp>
               <#assign selected = true>
             </#if>
             <#assign thisApp = StringUtil.wrapString(thisApp)>
             <#assign thisURL = thisApp>
             <#if thisApp != "/">
               <#assign thisURL = thisURL + "/control/main">
             </#if>
             <#if layoutSettings.suppressTab?exists && display.name == layoutSettings.suppressTab>
                 <!-- do not display this component&ndash;&gt;
             <#else>
               <#if uiLabelMap?exists>
                 <#assign desc = uiLabelMap[display.description]/>
                 <#assign title = uiLabelMap[display.title]/>
               <#else>
                 <#assign desc = display.description/>
                 <#assign title = display.title/>
               </#if>
                 <li class="treeview<#if selected>  active</#if>">

                   <#assign appUrl = thisApp?substring(1)/>
                   <#assign applicationMenuLocation = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLocation(appUrl)?default("")/>
                   <#assign applicationMenuName = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuName(appUrl)?default("")/>
                   <#assign uiLabels = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLabel(appUrl)?default("")/>
                   <#assign uistyle = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuStyle(appUrl)?default("")/>

                     <a href="${thisURL + externalKeyParam}" title="${desc}">
                         <i class="${uistyle}"></i><span>${title}</span>
                         <span class="fa fa-angle-left pull-right"></span>
                     </a>
                   <#if applicationMenuLocation != "" && applicationMenuName != "">

                   &lt;#&ndash;${setRequestAttribute("applicationMenuLocation", location)}&ndash;&gt;
                   &lt;#&ndash;${setRequestAttribute("applicationMenuName", name)}&ndash;&gt;
                   &lt;#&ndash;${setRequestAttribute("applicationUilabels", uiLabels)}&ndash;&gt;
                   &lt;#&ndash;${screens.render("component://admin/widget/CommonScreens.xml#displayMenu")}&ndash;&gt;

                     <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                       <ul class="treeview-menu">
                         <#assign menuItemList = appModelMenu.getMenuItemList()>
                         <#if menuItemList?exists>
                           <#list menuItemList as menuItem>
                             <#assign passed = true />
                             <#if (!menuItem.shouldBeRendered(context))>
                               <#assign  passed = false/>
                             </#if>

                             <#if passed>

                               <#assign hasSub = menuItem.getMenuItemList()?has_content/>
                               <#assign hasLink = menuItem.getLink()?exists/>
                               <#assign active = false/>
                               <#assign subActive = false/>
                               <#if menuItem.getLink()?exists>
                                 <#assign itemLink = menuItem.getLink()>
                                 <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+itemLink.getTarget(context))>
                                 <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                 <#if (currentURI = requestURI)>
                                   <#assign active = true/>
                                 <#elseif (("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index ==0)>
                                   <#assign active = true />
                                 </#if>
                               </#if>
                               <#if hasSub>
                                 <#assign subMenuItems = menuItem.getMenuItemList()>
                                 <#list subMenuItems as subMenuItem>
                                   <#assign subItemLink = subMenuItem.getLink()>
                                   <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                   <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                   <#if (currentURI == requestURI )>
                                     <#assign subActive = true/>
                                   <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                     <#assign subActive = true/>
                                   </#if>
                                 </#list>
                               </#if>


                                 <li<#if hasSub && (active ||subActive)> class="tree-view active"<#elseif (hasSub)> class="tree-view"<#elseif (active ||subActive)>
                                                                         class="active"</#if>>
                                   <#assign  itemTitle = menuItem.getTitle(context)>
                                   <#list uiLabels as uiLabel>
                                     <#assign itemTitle = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, itemTitle, locale)/>
                                   </#list>
                                   <#if hasLink>
                                     <#assign itemLink = menuItem.getLink()>
                                     <#assign commonDisplaying = itemLink.getText(context)>
                                     <#list uiLabels as uiLabel>
                                       <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                     </#list>
                                     <#if !commonDisplaying?starts_with("&#")>
                                       <#assign commonDisplaying = itemTitle>
                                     </#if>
                                       <#if itemLink.getParameterMap(context)?has_content>
                                           <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                       <#else >
                                           <#assign externalKeyParam1 = externalKeyParam/>
                                       </#if>
                                       <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}">
                                           <i class="fa fa-circle-o"></i>${commonDisplaying}<#if hasSub><span class="fa fa-angle-left pull-right"></span></#if></a>
                                   <#else>
                                       <a href="#"><i class="fa fa-circle-o"></i>${itemTitle}<#if hasSub><span
                                               class="fa fa-angle-left pull-right"></span></#if></a>
                                   </#if>
                                   <#if hasSub>
                                       <ul class="treeview-menu">
                                         <#assign subMenuItems = menuItem.getMenuItemList()>
                                         <#list subMenuItems as subMenuItem>
                                           <#if (!subMenuItem.shouldBeRendered(context))>
                                             <#assign  passed = false/>
                                           </#if>

                                           <#if passed>
                                             <#assign subItemLink = subMenuItem.getLink()>
                                             <#assign subCommonDisplaying = subItemLink.getText(context)>
                                             <#list uiLabels as uiLabel>
                                               <#assign subCommonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, subCommonDisplaying, locale)/>
                                             </#list>
                                             <#if !subCommonDisplaying?starts_with("&#")>
                                               <#assign subCommonDisplaying = subMenuItem.getTitle(context)>
                                             </#if>
                                             <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                             <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                             <#if (currentURI = requestURI )>
                                             <li class="active">
                                             <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                             <li class="active">
                                             <#else>
                                             <li>
                                             </#if>
                                               <#if subItemLink.getParameterMap(context)?has_content>
                                                   <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                               <#else >
                                                   <#assign externalKeyParam1 = externalKeyParam/>
                                               </#if>
                                               <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a></li>
                                           </#if>
                                         </#list>
                                       </ul>
                                   </#if>
                                 </li>

                             </#if>
                           </#list>
                         </#if>
                       </ul>
                   </#if>
                 </li>
             </#if>
           </#if>
         </#list>-->
        </ul>
    </section>
</aside>
</#if>