import org.ofbiz.entity.condition.*;
import org.ofbiz.entity.*;


// 获取专题活动链接类型
def linkTypeId = 'FIRST_LINK_TYPE'

def linkCond = EntityCondition.makeCondition('enumTypeId', EntityOperator.EQUALS, linkTypeId)

context.linkTypes = delegator.findList('Enumeration', linkCond,null,null,null,false)