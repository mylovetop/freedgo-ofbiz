webpackJsonp([19],{"Fx+V":function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=s("Dd8w"),a=s.n(i),o=s("fxnj"),n=s.n(o),r=s("4FCr"),l=s("S8Wg"),c=s("NYxO"),u=s("KeBu"),d=(a()({},Object(c.c)(["getptList"])),a()({},Object(c.b)(["myOrderGroupList"]),{gotoDetail:function(t){n.a.miniProgram.navigateTo({url:"/pages/pinTdetail/index?id="+t})},seeOrder:function(t){n.a.miniProgram.navigateTo({url:"/pages/webview/index?orderId="+t+"&path=orderDetail"})},goToPt:function(){n.a.miniProgram.redirectTo({url:"/pages/groupShopReco/index"})},goToDe:function(t){n.a.miniProgram.navigateTo({url:"/pages/goodDetail/index?id="+t+"&fr=l"})},getList:function(t,e,s){this.showLoading(),this.myOrderGroupList({flag:s,data:{page:t,pageSize:this.pageSize,status:e},this:this,method:this.myOrderGroupList})},addList:function(t){var e=this;this.$nextTick(function(){e.$refs.scrollerBottom.donePullup(),e.$refs.scrollerBottom.reset()})},onpulluploading:function(){var t=this;this.$refs.scrollerBottom.reset();var e=this.getptList.done.lists.length;e<this.getptList.done.max?this.getList(Math.ceil(e/this.pageSize),"TOGETHER_DONE",1):this.$nextTick(function(){t.$refs.scrollerBottom.disablePullup()})}}),r.a,l.a,u.a,{name:"collageued",data:function(){return{useUp:!0,pageSize:10,onFetching:!1,pullupDefaultConfig:{content:"上拉加载更多",pullUpHeight:60,height:40,autoRefresh:!1,downContent:"释放后加载",upContent:"上拉加载更多",loadingContent:"加载中...",clsPrefix:"xs-plugin-pullup-"}}},computed:a()({},Object(c.c)(["getptList"])),created:function(){this.getList(0,"TOGETHER_DONE",0)},methods:a()({},Object(c.b)(["myOrderGroupList"]),{gotoDetail:function(t){n.a.miniProgram.navigateTo({url:"/pages/pinTdetail/index?id="+t})},seeOrder:function(t){n.a.miniProgram.navigateTo({url:"/pages/webview/index?orderId="+t+"&path=orderDetail"})},goToPt:function(){n.a.miniProgram.redirectTo({url:"/pages/groupShopReco/index"})},goToDe:function(t){n.a.miniProgram.navigateTo({url:"/pages/goodDetail/index?id="+t+"&fr=l"})},getList:function(t,e,s){this.showLoading(),this.myOrderGroupList({flag:s,data:{page:t,pageSize:this.pageSize,status:e},this:this,method:this.myOrderGroupList})},addList:function(t){var e=this;this.$nextTick(function(){e.$refs.scrollerBottom.donePullup(),e.$refs.scrollerBottom.reset()})},onpulluploading:function(){var t=this;this.$refs.scrollerBottom.reset();var e=this.getptList.done.lists.length;e<this.getptList.done.max?this.getList(Math.ceil(e/this.pageSize),"TOGETHER_DONE",1):this.$nextTick(function(){t.$refs.scrollerBottom.disablePullup()})}}),filters:{deld:function(t){if(!t)return t;var e=t.indexOf(".");return-1==e?e:t.slice(0,e)}},components:{LoadMore:r.a,Scroller:l.a,empty:u.a}}),p={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticStyle:{height:"calc(100% - 0.8rem)"}},[t.getptList.done.max<=t.pageSize?i("div",{staticClass:"collage"},[i("ul",{staticClass:"drem2 content"},t._l(t.getptList.done.lists,function(e){return i("li",{key:e.togetherId},[i("div",{staticClass:"p20"},[i("p",{staticClass:"flexbox justifyx p1"},[i("span",[t._v(t._s("Y"===e.createUser?"您发起的拼团":"您参与的拼团"))]),i("span",{staticClass:"fred"},[t._v(t._s("TOGETHER_FAIL"==e.groupStatus?"未成团，已退款":"TOGETHER_DONE"==e.groupStatus?"已成团":"拼团中"))])]),t._v(" "),i("p",{staticClass:"p2"},[t._v(t._s(t._f("deld")(e.createDate)))])]),t._v(" "),t._l(e.products,function(e){return i("div",{key:e.productId,staticClass:"zjdiv p20 flexbox",on:{click:function(s){t.goToDe(e.productId)}}},[i("div",{staticClass:"imgdiv"},[i("img",{attrs:{src:e.mediumImageUrl}})]),t._v(" "),i("div",{staticClass:"flexbox flexdr around"},[i("p",{staticClass:"ms"},[t._v(t._s(e.productName))]),t._v(" "),i("p",{staticClass:"c999"},[i("span",{staticClass:"fred"},[t._v("￥"+t._s(e.activityPrice))]),i("span",[t._v("￥"+t._s(e.price))])]),t._v(" "),i("p",{staticClass:"c999"},[t._v(t._s(e.limitUserNum)+"人团")])])])}),t._v(" "),i("div",{staticClass:"flexbox justifyx wb centerx"},[i("div",{staticClass:"wbz"},t._l(e.personList,function(t,e){return i("img",{key:e,attrs:{src:t,alt:""}})})),t._v(" "),i("div",[i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.gotoDetail(e.togetherId)}}},[t._v("查看拼团")]),i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.seeOrder(e.orderId)}}},[t._v("查看订单")])])])],2)}))]):t._e(),t._v(" "),t.getptList.done.max>t.pageSize?i("scroller",{ref:"scrollerBottom",attrs:{"lock-x":"",height:"100%","pullup-config":t.pullupDefaultConfig,"use-pullup":t.useUp},on:{"on-pullup-loading":t.onpulluploading}},[i("div",{staticClass:"collage"},[i("ul",{staticClass:"drem2 content"},t._l(t.getptList.done.lists,function(e){return i("li",{key:e.togetherId},[i("div",{staticClass:"p20"},[i("p",{staticClass:"flexbox justifyx p1"},[i("span",[t._v(t._s("Y"===e.createUser?"您发起的拼团":"您参与的拼团"))]),i("span",{staticClass:"fred"},[t._v(t._s("TOGETHER_FAIL"==e.groupStatus?"未成团，已退款":"TOGETHER_DONE"==e.groupStatus?"已成团":"拼团中"))])]),t._v(" "),i("p",{staticClass:"p2"},[t._v(t._s(t._f("deld")(e.createDate)))])]),t._v(" "),t._l(e.products,function(e){return i("div",{key:e.productId,staticClass:"zjdiv p20 flexbox",on:{click:function(s){t.goToDe(e.productId)}}},[i("div",{staticClass:"imgdiv"},[i("img",{attrs:{src:e.mediumImageUrl}})]),t._v(" "),i("div",{staticClass:"flexbox flexdr around"},[i("p",{staticClass:"ms"},[t._v(t._s(e.productName))]),t._v(" "),i("p",{staticClass:"c999"},[i("span",{staticClass:"fred"},[t._v("￥"+t._s(e.activityPrice))]),i("span",[t._v("￥"+t._s(e.price))])]),t._v(" "),i("p",{staticClass:"c999"},[t._v(t._s(e.limitUserNum)+"人团")])])])}),t._v(" "),i("div",{staticClass:"flexbox justifyx wb centerx"},[i("div",{staticClass:"wbz"},t._l(e.personList,function(t,e){return i("img",{key:e,attrs:{src:t,alt:""}})})),t._v(" "),i("div",[i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.gotoDetail(e.togetherId)}}},[t._v("查看拼团")]),i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.seeOrder(e.orderId)}}},[t._v("查看订单")])])])],2)}))])]):t._e(),t._v(" "),t.getptList.done.lists.length||t.getptList.done.t?t._e():i("empty",[i("img",{staticStyle:{width:"4.45rem",height:"3.56rem"},attrs:{slot:"img",src:s("z+D9"),alt:""},slot:"img"}),t._v(" "),i("button",{staticClass:"btnn  bred",attrs:{slot:"an"},on:{click:t.goToPt},slot:"an"},[t._v("去拼团")])])],1)},staticRenderFns:[]};var g=s("VU/8")(d,p,!1,function(t){s("p0PT"),s("SArz")},"data-v-1a674104",null);e.default=g.exports},SArz:function(t,e){},p0PT:function(t,e){}});
//# sourceMappingURL=19.74b12f3ee1e562ba2f4e.js.map