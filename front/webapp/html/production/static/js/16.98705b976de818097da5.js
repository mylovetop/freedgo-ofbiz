webpackJsonp([16],{"5UTY":function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=s("Dd8w"),n=s.n(i),o=s("qV47"),a=s("fxnj"),c=s.n(a),r=s("NYxO"),l=s("4FCr"),h=s("S8Wg"),u=s("KeBu"),d=(n()({},Object(r.c)({getfrWishlt:"getmyWishlt"})),n()({},Object(r.b)(["friendsWish"]),{goToIndex:function(){c.a.miniProgram.switchTab({url:"/pages/home/index"})},seeDetail:function(t){c.a.miniProgram.navigateTo({url:"/pages/webview/index?path=wishDetail&wishId="+t+"&mine=1"})},seeOrder:function(){c.a.miniProgram.navigateTo({url:"/pages/webview/index?path=myOrder"})},getList:function(t,e){this.showLoading(),this.friendsWish({this:this,flag:e,data:{page:t,pageSize:this.pageSize,status:"WISH_HAS_ACHIEVE"},method:this.friendsWish})},ontouchstart:function(t){this.sy=t.changedTouches[0].pageY},ontouchend:function(t){this.ey=t.changedTouches[0].pageY,this.sy-this.ey>30&&this.bindScroll()},jizw:function(){this.onFetching=!1},bindScroll:function(){var t=this.getfrWishlt.list.length;if(t==this.getfrWishlt.max)return this.tips="没有更多了",void(this.onFetching=!0);var e=this.$refs.content.scrollTop,s=this.$refs.content.clientHeight;this.$refs.content1.clientHeight-e<s&&(this.onFetching||(this.onFetching=!0,this.tips="正在加载",this.getList(Math.ceil(t/this.pageSize),!0)))},test:function(t){console.log(t),t.stopPropagation()},onScrollBottom:function(){var t=this;this.onFetching||(this.onFetching=!0,setTimeout(function(){console.log(8),t.$nextTick(function(){t.$refs.scrollerBottom.reset()}),t.onFetching=!1},2e3))}}),l.a,h.a,o.a,u.a,{name:"alreadyrealizedf",data:function(){return{pageSize:10,tips:"",onFetching:!1,obj:null,nu:2,sy:"",ey:""}},filters:{statusFls:function(t){var e=void 0;switch(t){case"WISH_WAIT_ACHIEVE":e="待实现";break;case"WISH_WAIT_PAY":e="待付款，已经生成订单";break;case"WISH_WAIT_ACHIEVE":e="待实现";break;case"WISH_HAS_ACHIEVE":e="已实现"}return e}},mounted:function(){document.title="好友心愿",this.$route.query.token&&localStorage.setItem("token",decodeURIComponent(this.$route.query.token)),this.$route.query.refreshToken&&localStorage.setItem("refreshToken",decodeURIComponent(this.$route.query.refreshToken)),this.getList(0,"")},computed:n()({},Object(r.c)({getfrWishlt:"getmyWishlt"})),methods:n()({},Object(r.b)(["friendsWish"]),{goToIndex:function(){c.a.miniProgram.switchTab({url:"/pages/home/index"})},seeDetail:function(t){c.a.miniProgram.navigateTo({url:"/pages/webview/index?path=wishDetail&wishId="+t+"&mine=1"})},seeOrder:function(){c.a.miniProgram.navigateTo({url:"/pages/webview/index?path=myOrder"})},getList:function(t,e){this.showLoading(),this.friendsWish({this:this,flag:e,data:{page:t,pageSize:this.pageSize,status:"WISH_HAS_ACHIEVE"},method:this.friendsWish})},ontouchstart:function(t){this.sy=t.changedTouches[0].pageY},ontouchend:function(t){this.ey=t.changedTouches[0].pageY,this.sy-this.ey>30&&this.bindScroll()},jizw:function(){this.onFetching=!1},bindScroll:function(){var t=this.getfrWishlt.list.length;if(t==this.getfrWishlt.max)return this.tips="没有更多了",void(this.onFetching=!0);var e=this.$refs.content.scrollTop,s=this.$refs.content.clientHeight;this.$refs.content1.clientHeight-e<s&&(this.onFetching||(this.onFetching=!0,this.tips="正在加载",this.getList(Math.ceil(t/this.pageSize),!0)))},test:function(t){console.log(t),t.stopPropagation()},onScrollBottom:function(){var t=this;this.onFetching||(this.onFetching=!0,setTimeout(function(){console.log(8),t.$nextTick(function(){t.$refs.scrollerBottom.reset()}),t.onFetching=!1},2e3))}}),components:{LoadMore:l.a,Scroller:h.a,backHome:o.a,empty:u.a}}),g={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{ref:"content",staticStyle:{height:"calc(100% - 0.8rem)",overflow:"hidden","overflow-y":"auto"},on:{touchend:t.ontouchend,touchstart:t.ontouchstart}},[t.getfrWishlt.list&&!t.getfrWishlt.list.length?i("empty",[i("img",{staticStyle:{width:"1.89rem",height:"1.91rem"},attrs:{slot:"img",src:s("OrkP"),alt:""},slot:"img"}),t._v(" "),i("button",{staticClass:"btnn  bred",attrs:{slot:"an"},on:{click:t.goToIndex},slot:"an"},[t._v("去首页")])]):t._e(),t._v(" "),i("ul",{ref:"content1",staticClass:"drem2 content"},[t._l(t.getfrWishlt.list,function(e){return[1==e.products.length?i("li",{ref:"last"+e,refInFor:!0,class:{lili:10==e}},[i("div",{staticClass:"p20 bf"},[i("p",{staticClass:"flexbox justifyx p1"},[i("span",[t._v(t._s(e.sendPartyName)+"的心愿")]),i("span",{staticClass:"money"},[t._v("已实现")])]),t._v(" "),i("p",{staticClass:"p2"},[t._v(t._s(e.sendDate))])]),t._v(" "),i("div",{staticClass:"zjdiv  flexbox"},[i("div",{staticClass:"imgdiv"},[i("img",{attrs:{src:e.products[0].imgUrl}})]),t._v(" "),i("div",{staticClass:"flexbox flexdr centery",staticStyle:{flex:"1"}},[i("p",{staticClass:"ms"},[t._v(t._s(e.products[0].productName))]),t._v(" "),i("div",{staticClass:"bq"},t._l(e.products[0].feature,function(e){return i("p",{key:e.productFeatureName},[t._v(t._s(e.productFeatureTypeName+"："+e.productFeatureName))])}))])]),t._v(" "),i("div",{staticClass:"flexbox justifyx wb centerx bf"},[i("div",{staticClass:"wbz"},[t._v("\n            共"+t._s(e.products.length)+"件，总金额 "),i("span",{staticClass:"money bold"},[t._v("￥"+t._s(e.sumPrice))])]),t._v(" "),i("div",[i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.seeDetail(e.wishId)}}},[t._v("查看心愿")]),i("button",{staticClass:"btnn",on:{click:function(e){e.stopPropagation(),t.seeOrder(e)}}},[t._v("查看订单")])])])]):i("li",{ref:"last"+e,refInFor:!0},[i("div",{staticClass:"p20 bf"},[i("p",{staticClass:"flexbox justifyx p1"},[i("span",[t._v(t._s(e.sendPartyName)+"的心愿")]),i("span",{staticClass:"money"},[t._v("已实现")])]),t._v(" "),i("p",{staticClass:"p2"},[t._v(t._s(e.sendDate))])]),t._v(" "),i("div",{staticClass:"zjdiv p20 multy bf"},[i("scroller",{attrs:{"lock-y":"","scrollbar-x":!1,width:"100%"}},[i("ul",{staticClass:"flexbox boxwrap",style:{width:2.67*e.products.length+2.14+.4+"rem"}},t._l(e.products,function(e,s){return i("li",{key:e.productId+s},[i("img",{staticClass:"goods_img",attrs:{src:e.imgUrl}}),t._v(" "),i("p",[t._v(t._s(e.productName))])])}))])],1),t._v(" "),i("div",{staticClass:"flexbox justifyx wb centerx bf"},[i("div",{staticClass:"wbz"},[t._v("\n            共"+t._s(e.products.length)+"件，总金额 "),i("span",{staticClass:"money bold"},[t._v("￥"+t._s(e.sumPrice))])]),t._v(" "),i("div",[i("button",{staticClass:"btnn",on:{click:function(s){s.stopPropagation(),t.seeDetail(e.wishId)}}},[t._v("查看心愿")]),i("button",{staticClass:"btnn",on:{click:function(e){e.stopPropagation(),t.seeOrder(e)}}},[t._v("查看订单")])])])])]})],2),t._v(" "),t.onFetching?i("load-more",{attrs:{tip:t.tips,"show-loading":!1}}):t._e(),t._v(" "),"share"==t.$route.query.csi?i("back-home"):t._e()],1)},staticRenderFns:[]};var f=s("VU/8")(d,g,!1,function(t){s("FE5G"),s("bL5h")},"data-v-1e96a0a4",null);e.default=f.exports},FE5G:function(t,e){},bL5h:function(t,e){}});
//# sourceMappingURL=16.98705b976de818097da5.js.map