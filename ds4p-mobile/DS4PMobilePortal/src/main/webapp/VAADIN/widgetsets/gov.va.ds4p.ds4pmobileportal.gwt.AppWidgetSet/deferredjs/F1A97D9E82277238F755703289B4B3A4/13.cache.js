/*
 * This software is being provided for technology demonstration purposes only.
 * Use of Vaadin Touchkit Add-on API are provided via Affero General Public License
 * (APGL 3.0).  Please refer the APGL 3.0 at www.gnu.org for further details.
 *
 * Items outside of the use of Vaadin Touchkit Add-on API are being provided per
 * FARS 52.227-14 Rights in Data - General.  Any redistribution or request for
 * copyright requires written consent by the Department of Veterans Affairs.
 */
function wL(){}
function rL(){}
function cvb(){}
function bvb(){}
function KBb(){}
function aCb(){}
function fCb(){}
function LBb(b,c){w_(b,c,b.b)}
function MBb(b,c){return tqb(b.d,b,c)}
function iCb(b,c){b.f=yr(c,26);R1(b,c)}
function bCb(b,c){this.b=b;this.n=new bzb(this);this.p=c;this.c=Owc}
function mCb(b,c){this.i=b;this.Ob=$doc.createElement(Qrc);this.f=c;this.Ob[trc]='v-absolutelayout-wrapper'}
function QBb(b){var c,d;for(d=new F9(b.Ib);d.b<d.c.d-1;){c=E9(d);c!=null&&c.cM&&!!c.cM[140]&&gCb(yr(c,140))}}
function PBb(b){var c,d,e;for(e=new F9(b.Ib);e.b<e.c.d-1;){d=E9(e);if(d!=null&&d.cM&&!!d.cM[140]){c=yr(d,140);Mib(b.d,c.yb);kCb(c)}}}
function lCb(b,c){var d;hCb(b,c[1][Pwc]);b.yb!=b.f&&iCb(b,yr(b.f,70));d=c[2];b.f.fe(d,b.i.d);qtc in d[1]&&Mib(b.i.d,yr(b.f,70))}
function jCb(b,c){var d;d=Erb(c);if(d){if(!b.c){b.c=new Brb(b.f,b.i.d);LBb(b.i,b.c)}b.c.Te(c);kCb(b)}else{if(b.c){_$(b.c);b.c=null}}}
function NBb(b,c,d){var e;e=yr(b.g.vg(d[1][Mrc]),140);if(!e){e=new mCb(b,Hib(c,d));b.g.wg(d[1][Mrc],e);w_(b,e,b.b)}return e}
function yL(){uL=new wL;Ub((Sb(),Rb),13);!!$stats&&$stats(xc(aBc,Xpc,-1,-1));uL.Qc();!!$stats&&$stats(xc(aBc,$xc,-1,-1))}
function kCb(b){var c;if(b.c){c=b.c.Ob.style;c[iqc]=lqc;c[Brc]=(b.Ob.offsetLeft||0)+(Pi(),src);c[Crc]=(b.Ob.offsetTop||0)-urb(b.c)+src}}
function OBb(b,c,d){if(!b.f){b.f=$doc.createElement(Qrc);b.f.style[iqc]=lqc;b.b.appendChild(b.f)}if(d){b.f.style[Arc]=c;return b.f.offsetHeight||0}else{b.f.style[vrc]=c;return b.f.offsetWidth||0}}
function vL(){var b,c,d;while(sL){d=nb;sL=sL.b;!sL&&(tL=null);if(!d){(pub(),oub).wg(gC,new cvb);Jlb()}else{try{(pub(),oub).wg(gC,new cvb);Jlb()}catch(b){b=oJ(b);if(Ar(b,37)){c=b;Lrb.He(c)}else throw b}}}}
function RBb(){this.Ib=new A9(this);this.b=$doc.createElement(Qrc);this.g=new Dnc;this.c=new bCb(this,this);this.Ob=$doc.createElement(Qrc);this.Ob[trc]='v-absolutelayout';this.e=$doc.createElement(Qrc);this.b.style[iqc]=vtc;this.b.style[msc]=Src;this.e.appendChild(this.b);this.Ob.appendChild(this.e)}
function gCb(b){var c,d,e,f,g;f=b.Ob.style;if(b.b!=null&&b.j!=null){c=OBb(b.i,b.b,true);Lrb.Je(bBc+c);d=(b.i.b.offsetHeight||0)-c-(b.Ob.offsetTop||0);Lrb.Je(bBc+d);d<0&&(d=0);f[Arc]=d+(Pi(),src)}else{f[Arc]=Spc}if(b.e!=null&&b.g!=null){e=OBb(b.i,b.g,false);Lrb.Je(cBc+e);g=(b.i.b.offsetWidth||0)-e-(b.Ob.offsetLeft||0);Lrb.Je(cBc+g);g<0&&(g=0);f[vrc]=g+(Pi(),src)}else{f[vrc]=Spc}}
function hCb(b,c){var d,e,f,g;if(b.d==null||!yhc(b.d,c)){b.d=c;b.j=b.g=b.b=b.e=b.k=null;if(!yhc(b.d,Spc)){f=Chc(b.d,Ovc,0);for(d=0;d<f.length;++d){e=Chc(f[d],dqc,0);yhc(e[0],Brc)?(b.e=e[1]):yhc(e[0],Crc)?(b.j=e[1]):yhc(e[0],csc)?(b.g=e[1]):yhc(e[0],dsc)?(b.b=e[1]):yhc(e[0],'z-index')&&(b.k=e[1])}}g=b.Ob.style;b.k!=null?(g[vsc]=b.k,undefined):(g[vsc]=Spc,undefined);g[Crc]=b.j;g[Brc]=b.e;g[csc]=b.g;g[dsc]=b.b;knb((Umb(),!Tmb&&(Tmb=new qnb),Umb(),Tmb))&&gCb(b)}kCb(b)}
var bBc='ALB',cBc='ALR',aBc='runCallbacks13';_=wL.prototype=rL.prototype=new J;_.gC=function xL(){return yu};_.Qc=function BL(){vL()};_.cM={};_=cvb.prototype=bvb.prototype=new J;_.Ue=function dvb(){return new RBb};_.gC=function evb(){return NA};_.cM={137:1};_=RBb.prototype=KBb.prototype=new q$;_.rd=function SBb(b){w_(this,b,this.b)};_.je=function TBb(b){var c,d,e;e=yr(b.kd(),140);e.e!=null&&e.g!=null?(d=parseInt(e.Ob[zrc])||0):e.g!=null?(d=(parseInt(e.Ob[zrc])||0)+(e.Ob.offsetLeft||0)):(d=(this.b.offsetWidth||0)-(e.Ob.offsetLeft||0));e.j!=null&&e.b!=null?(c=parseInt(e.Ob[yrc])||0):e.b!=null?(c=(e.Ob.offsetTop||0)+(parseInt(e.Ob[yrc])||0)):(c=(this.b.offsetHeight||0)-(e.Ob.offsetTop||0));return new lpb(d,c)};_.gC=function UBb(){return gC};_.ke=function VBb(b,c){var d,e,f;for(f=new F9(this.Ib);f.b<f.c.d-1;){e=E9(f);d=yr(e,140);if(d.yb==b){d.f=yr(c,26);R1(d,c);return}}};_.le=function WBb(b){return true};_.dd=function XBb(b){this.Ob.style[Arc]=b;this.b.style[Arc]=b;if(!this.i){knb((Umb(),!Tmb&&(Tmb=new qnb),Umb(),Tmb))&&QBb(this);PBb(this)}};_.ed=function YBb(b){this.Ob[trc]=b};_.gd=function ZBb(b){this.Ob.style[vrc]=b;this.b.style[vrc]=b;if(!this.i){knb((Umb(),!Tmb&&(Tmb=new qnb),Umb(),Tmb))&&QBb(this);PBb(this)}};_.me=function $Bb(b,c){var d;d=yr(yr(b,70).kd(),140);jCb(d,c)};_.fe=function _Bb(b,c){var d,e,f,g,i,k,n,o,p,q;this.i=true;this.d=c;if(gjb(c,this,b,true)){this.i=false;return}Uyb(this.c,c);n=new Mnc(Eic(this.g));for(f=new Tpb(b);o=f.c.length-2,o>f.b+1;){e=zr(Spb(f));if(yhc(e[0],'cc')){g=e[2];n.b.xg(g[1][Mrc])!=null;lCb(NBb(this,c,g),e)}}for(k=(p=Eic(n.b).c.ud(),new Ekc(p));k.b.Sc();){i=yr((q=yr(k.b.Tc(),58),q.Cg()),1);d=yr(this.g.vg(i),140);this.g.xg(i);!!d.c&&_$(d.c);fjb(d.i.d,d.f);_$(d)}this.i=false};_.cM={10:1,13:1,15:1,20:1,21:1,22:1,24:1,26:1,33:1,69:1,70:1,75:1};_.d=null;_.e=null;_.f=null;_.i=false;_=bCb.prototype=aCb.prototype=new Uzb;_.af=function cCb(b){return MBb(this.b,b)};_.gC=function dCb(){return eC};_._e=function eCb(b,c){return V$(this.b,b,c)};_.cM={12:1,40:1,42:1,46:1,49:1};_.b=null;_=mCb.prototype=fCb.prototype=new O1;_.gC=function nCb(){return fC};_.Hd=function oCb(b){this.f=yr(b,26);R1(this,b)};_.cM={10:1,13:1,15:1,17:1,19:1,20:1,21:1,22:1,33:1,69:1,70:1,75:1,76:1,140:1};_.b=null;_.c=null;_.d=null;_.e=null;_.f=null;_.g=null;_.i=null;_.j=null;_.k=null;var yu=dgc(Lxc,'AsyncLoader13'),NA=dgc(Vxc,'WidgetMapImpl$18$1'),eC=dgc(Uxc,'VAbsoluteLayout$1'),fC=dgc(Uxc,'VAbsoluteLayout$AbsoluteWrapper');Opc(yL)();