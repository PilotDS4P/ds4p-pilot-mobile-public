/*
 * This software is being provided for technology demonstration purposes only.
 * Use of Vaadin Touchkit Add-on API are provided via Affero General Public License
 * (APGL 3.0).  Please refer the APGL 3.0 at www.gnu.org for further details.
 *
 * Items outside of the use of Vaadin Touchkit Add-on API are being provided per
 * FARS 52.227-14 Rights in Data - General.  Any redistribution or request for
 * copyright requires written consent by the Department of Veterans Affairs.
 */
function OR(){}
function JR(){}
function $wb(){}
function Zwb(){}
function k_b(){}
function B_b(){}
function G_b(){}
function K_b(){}
function O_b(){}
function kgc(){}
function rgc(b){this.b=b}
function H_b(b){this.b=b}
function L_b(b){this.b=b}
function C_b(b){neb(b);u_b(b.b)}
function P_b(b,c,d){this.b=b;this.c=c;this.d=d}
function D_b(b){this.b=b;teb.call(this,true,false)}
function v_b(b){xib(b.f,b.q,osc,Spc+b.y.b,b.r,100)}
function a4(){$3.call(this);Q2(this.s,Spc,true);this.Ob.style[tvc]=uvc}
function o_b(b,c){if(CZ(c.type)==4){if(!b.i&&!b.u&&!b.j){t_b(b,c,true);c.cancelBubble=true}}}
function sgc(b,c){if(isNaN(b)){return isNaN(c)?0:1}else if(isNaN(c)){return -1}return b<c?-1:b>c?1:0}
function q_b(b,c){var d;d=Spc+c;b.v==0&&(d=Spc+~~Math.max(Math.min((new rgc(c)).b,2147483647),-2147483648));b.k.Bd(d)}
function QR(){MR=new OR;Ub((Sb(),Rb),30);!!$stats&&$stats(xc(rCc,Xpc,-1,-1));MR.Qc();!!$stats&&$stats(xc(rCc,$xc,-1,-1))}
function r_b(b){var c;b.d.style[Arc]=Zqc;b.d.style[msc]=Src;c=parseInt(b.Ob[yrc])||0;c<50&&(c=50);b.d.style[Arc]=c+src;b.d.style[msc]=Spc}
function NR(){var b,c,d;while(KR){d=nb;KR=KR.b;!KR&&(LR=null);if(!d){(pub(),oub).wg(cF,new $wb);Jlb()}else{try{(pub(),oub).wg(cF,new $wb);Jlb()}catch(b){b=oJ(b);if(Ar(b,37)){c=b;Lrb.He(c)}else throw b}}}}
function u_b(b){b.z?jeb(b.n,(pe(b.o)|0)+(b.o.offsetWidth||0),(qe(b.o)|0)+~~((b.o.offsetHeight||0)/2)-~~((parseInt(b.n.Ob[yrc])||0)/2)):jeb(b.n,(pe(b.o)|0)+~~((b.o.offsetWidth||0)/2)-~~((parseInt(b.n.Ob[zrc])||0)/2),(qe(b.o)|0)-(parseInt(b.n.Ob[yrc])||0))}
function l_b(b){var c,d,e,f,g;f=b.z?Arc:vrc;d=b.z?vrc:Arc;c=b.z?yrc:zrc;b.d.style[d]=Spc;e=(g=b.Ob.parentNode,(!g||g.nodeType!=1)&&(g=null),g);if((parseInt(e[c])||0)>50){b.z?r_b(b):(b.d.style[f]=Spc,undefined)}else{b.d.style[f]=Kvc;jtb((Gc(),Fc),new P_b(b,c,f))}}
function m_b(b){var c,d,e,f,g,i,k;k=b.z?Arc:vrc;e=b.z?Wsc:utc;f=b.z?utc:Wsc;d=b.z?yrc:zrc;b.o.style[e]=Zqc;b.o.style[f]=Spc;if(b.w){i=~~Math.max(Math.min(ngc(ee(b.d,d))/100*b.p,2147483647),-2147483648);if(b.p==-1){c=ogc(ee(b.d,d));g=(b.s-b.t)*(b.v+1)*3;i=~~Math.max(Math.min(c-g,2147483647),-2147483648)}i<3&&(i=3);b.o.style[k]=i+src}else{b.o.style[k]=Spc}b.o.style[Trc]=Urc}
function n_b(b,c,d,e){var f;if(d){return false}if(c==38&&b.z||c==39&&!b.z){if(e){for(f=0;f<b.b;++f){s_b(b,new rgc(b.y.b+Math.pow(10,-b.v)),false)}++b.b}else{s_b(b,new rgc(b.y.b+Math.pow(10,-b.v)),false)}return true}else if(c==40&&b.z||c==37&&!b.z){if(e){for(f=0;f<b.b;++f){s_b(b,new rgc(b.y.b-Math.pow(10,-b.v)),false)}++b.b}else{s_b(b,new rgc(b.y.b-Math.pow(10,-b.v)),false)}return true}return false}
function s_b(b,c,d){var e,f,g,i,k,n,o,p;if(!c){return}c.b<b.t?(c=new rgc(b.t)):c.b>b.s&&(c=new rgc(b.s));n=b.z?Wsc:utc;f=b.z?yrc:zrc;g=ogc(ee(b.o,f));e=ogc(ee(b.d,f))-2;k=e-g;o=c.b;if(b.v>0){o=RJ(GJ(khc(o*Math.pow(10,b.v))));o=o/Math.pow(10,b.v)}else{o=RJ(GJ(Math.round(o)))}p=b.s-b.t;i=0;p>0&&(i=k*((o-b.t)/p));i<0&&(i=0);b.z&&(i=k-i-(knb((Umb(),!Tmb&&(Tmb=new qnb),Umb(),Tmb))?1:0));b.o.style[n]=TJ(GJ(Math.round(i)))+src;b.y=new rgc(o);q_b(b,o);d&&xib(b.f,b.q,osc,Spc+b.y.b,b.r,100)}
function t_b(b,c,d){var e,f,g,i,k;g=b.z?(bqb(),c.type.indexOf(wtc)!=-1?c.changedTouches[0].clientY|0:c.clientY||0):(bqb(),c.type.indexOf(wtc)!=-1?c.changedTouches[0].clientX|0:c.clientX||0);if(b.z){i=b.o.offsetHeight||0;f=b.d.offsetHeight||0;e=(qe(b.d)|0)-($doc.body.scrollTop||0)-~~(i/2)}else{i=b.o.offsetWidth||0;f=b.d.offsetWidth||0;e=(pe(b.d)|0)-re($doc.body)+~~(i/2)}b.z?(k=(f-(g-e))/(f-i)*(b.s-b.t)+b.t):(k=(g-e)/(f-i)*(b.s-b.t)+b.t);k<b.t?(k=b.t):k>b.s&&(k=b.s);s_b(b,new rgc(k),d)}
function p_b(b,c){switch(CZ(c.type)){case 4:case 1048576:if(!b.i&&!b.u){Q9();aab(b.Ob);C_b(b.n);b.j=true;b.o[trc]='v-slider-handle v-slider-handle-active';BY(b.Ob);c.preventDefault();c.cancelBubble=true;c.stopPropagation();Lrb.Je('Slider move start')}break;case 64:case 2097152:if(b.j){Lrb.Je('Slider move');t_b(b,c,false);b.z?jeb(b.n,(pe(b.o)|0)+(b.o.offsetWidth||0),(qe(b.o)|0)+~~((b.o.offsetHeight||0)/2)-~~((parseInt(b.n.Ob[yrc])||0)/2)):jeb(b.n,(pe(b.o)|0)+~~((b.o.offsetWidth||0)/2)-~~((parseInt(b.n.Ob[zrc])||0)/2),(qe(b.o)|0)-(parseInt(b.n.Ob[yrc])||0));c.stopPropagation()}break;case 4194304:b2(b.n,false);Kqb();case 8:Lrb.Je('Slider move end');b.j=false;b.o[trc]=sCc;AY(b.Ob);t_b(b,c,true);c.stopPropagation();}}
function w_b(){this.Ob=$doc.createElement(Qrc);this.Ad(0);this.k=new a4;this.n=new D_b(this);this.g=new mNb(100,new H_b(this));this.d=$doc.createElement(Qrc);this.o=$doc.createElement(Qrc);this.x=$doc.createElement(Qrc);this.e=$doc.createElement(Qrc);this.Ob[trc]='v-slider';this.d[trc]='v-slider-base';this.o[trc]=sCc;this.x[trc]='v-slider-smaller';this.e[trc]='v-slider-bigger';this.Ob.appendChild(this.e);this.Ob.appendChild(this.x);this.Ob.appendChild(this.d);this.d.appendChild(this.o);this.x.style[lsc]=rqc;this.e.style[lsc]=rqc;this.o.style[Trc]=Src;this.Lb==-1?CY(this.Ob,15866876|(this.Ob.__eventBits||0)):(this.Lb|=15866876);O$(this.n.bd(),'v-slider-feedback',true);g2(this.n,this.k);this.Lb==-1?CY(this.Ob,241|(this.Ob.__eventBits||0)):(this.Lb|=241)}
var rCc='runCallbacks30',sCc='v-slider-handle',tCc='v-slider-vertical';_=OR.prototype=JR.prototype=new J;_.gC=function PR(){return Bv};_.Qc=function TR(){NR()};_.cM={};_=a4.prototype=Q3.prototype;_=$wb.prototype=Zwb.prototype=new J;_.Ue=function _wb(){return new w_b};_.gC=function axb(){return lB};_.cM={137:1};_=w_b.prototype=k_b.prototype=new fzb;_.gC=function x_b(){return cF};_.Me=function y_b(){this.z&&r_b(this);s_b(this,this.y,false)};_.Vc=function z_b(b){var c,d;if(this.i||this.u){return}c=b.target;if(CZ(b.type)==131072){d=Math.round(-b.wheelDelta/40)||0;d<0?s_b(this,new rgc(this.y.b+Math.pow(10,-this.v)),false):s_b(this,new rgc(this.y.b-Math.pow(10,-this.v)),false);lNb(this.g);b.preventDefault();b.cancelBubble=true}else if(this.j||c==this.o){p_b(this,b)}else if(c==this.x){s_b(this,new rgc(this.y.b-Math.pow(10,-this.v)),true)}else if(c==this.e){s_b(this,new rgc(this.y.b+Math.pow(10,-this.v)),true)}else if(CZ(b.type)==124){o_b(this,b)}else if((Umb(),!Tmb&&(Tmb=new qnb),Umb(),Tmb).b.g&&CZ(b.type)==256||!(!Tmb&&(Tmb=new qnb),Tmb).b.g&&CZ(b.type)==128){if(n_b(this,b.keyCode||0,!!b.ctrlKey,!!b.shiftKey)){C_b(this.n);lNb(this.g);b.preventDefault();b.cancelBubble=true}}else c==this.Ob&&CZ(b.type)==2048?C_b(this.n):c==this.Ob&&CZ(b.type)==4096?(b2(this.n,false),Kqb()):CZ(b.type)==4&&C_b(this.n);bqb();if(b.type.indexOf(wtc)!=-1){b.preventDefault();b.stopPropagation()}!!this.f&&(stb(this.f.I,b,this,null),undefined)};_.fe=function A_b(b,c){var d;this.f=c;this.q=b[1][Mrc];if(gjb(c,this,b,true)){return}this.r=Boolean(b[1][Psc]);this.i=Boolean(b[1][Drc]);this.u=Boolean(b[1][Msc]);this.z=Awc in b[1];this.c='arrows' in b[1];d=Spc;wqc in b[1]&&(d=b[1][wqc]);this.w=d.indexOf('scrollbar')>-1;if(this.c){this.x.style[lsc]=sqc;this.e.style[lsc]=sqc}this.z?O$(this.Ob,tCc,true):O$(this.Ob,tCc,false);this.t=b[1][zzc];this.s=b[1]['max'];this.v=b[1]['resolution'];this.y=new rgc(b[1][Ssc][osc]);q_b(this,this.y.b);this.p=b[1]['hsize'];l_b(this);if(this.z){m_b(this);s_b(this,this.y,false)}else{jtb((Gc(),Fc),new L_b(this))}};_.cM={10:1,13:1,15:1,17:1,19:1,20:1,21:1,22:1,25:1,26:1,33:1,69:1,70:1,75:1,76:1,124:1,126:1,131:1,132:1};_.b=1;_.c=false;_.d=null;_.e=null;_.f=null;_.i=false;_.j=false;_.o=null;_.p=0;_.q=null;_.r=false;_.s=0;_.t=0;_.u=false;_.v=0;_.w=false;_.x=null;_.y=null;_.z=false;_=D_b.prototype=B_b.prototype=new beb;_.gC=function E_b(){return $E};_.Md=function F_b(){neb(this);u_b(this.b)};_.cM={9:1,10:1,11:1,12:1,13:1,15:1,16:1,17:1,18:1,19:1,20:1,21:1,22:1,23:1,33:1,69:1,70:1,72:1,75:1,76:1,77:1};_.b=null;_=H_b.prototype=G_b.prototype=new J;_.Zb=function I_b(){v_b(this.b);this.b.b=1};_.gC=function J_b(){return _E};_.cM={3:1};_.b=null;_=L_b.prototype=K_b.prototype=new J;_.Zb=function M_b(){m_b(this.b);s_b(this.b,this.b.y,false)};_.gC=function N_b(){return aF};_.cM={3:1,14:1};_.b=null;_=P_b.prototype=O_b.prototype=new J;_.Zb=function Q_b(){var b,c;b=(c=this.b.Ob.parentNode,(!c||c.nodeType!=1)&&(c=null),c);if((parseInt(b[this.c])||0)>55){this.b.z?r_b(this.b):(this.b.d.style[this.d]=Spc,undefined);s_b(this.b,this.b.y,false)}};_.gC=function R_b(){return bF};_.cM={3:1,14:1};_.b=null;_.c=null;_.d=null;_=rgc.prototype=kgc.prototype=new lgc;_.cT=function tgc(b){return sgc(this.b,yr(b,121).b)};_.eQ=function ugc(b){return b!=null&&b.cM&&!!b.cM[121]&&yr(b,121).b==this.b};_.gC=function vgc(){return uH};_.hC=function wgc(){return ~~Math.max(Math.min(this.b,2147483647),-2147483648)};_.tS=function xgc(){return Spc+this.b};_.cM={30:1,32:1,109:1,121:1};_.b=0;var Bv=dgc(Lxc,'AsyncLoader30'),lB=dgc(Vxc,'WidgetMapImpl$41$1'),$E=dgc(Uxc,'VSlider$1'),_E=dgc(Uxc,'VSlider$2'),aF=dgc(Uxc,'VSlider$3'),bF=dgc(Uxc,'VSlider$4'),uH=dgc(vxc,'Double');Opc(QR)();