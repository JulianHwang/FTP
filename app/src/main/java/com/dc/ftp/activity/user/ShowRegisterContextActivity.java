package com.dc.ftp.activity.user;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.dc.ftp.R;
import com.dc.ftp.base.SPBaseActivity;
import com.dc.ftp.global.Default;
import com.dc.ftp.http.BaseHttpClient;
import com.dc.ftp.http.JsonBuilder;
import com.dc.ftp.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ShowRegisterContextActivity extends SPBaseActivity {

	private WebView webview;
	private String registerRule="本网站由德成财富投资管理（常州）有限公司（下称“德成贷”）负责运营。" +
			"本注册协议双方为德成贷与德成贷注册用户（下称“用户”或“您”），本注册协议具有法律效力效力。" +
			"请您在注册成为本网站用户前务必仔细阅读以下条款。若您一旦注册，则表示您同意接受本网站的服务，并同意受以下条款的约束。" +
			"若您不同意接受以下条款，请立即离开本网站。\n本注册协议内容包括以下条款及已经发布的或将来可能发布的各类规则。所有规则为协议不可分割的一部分，与协议正文具有同等法律效力。本协议是由您与本网站共同签订的，适用于您在本网站的全部活动。在您注册成为用户时，您已经阅读、理解并接受本协议的全部条款及各类规则，并承诺遵守中国现行的法律、法规、规章及其他政府规定，如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。\n本网站有权根据需要不时地制定、修改本协议或各类规则，如本条款及规则有任何变更，一切变更以本网站最新公布条例为准。德成贷将随时刊载并公告本协议及规则的变更情况，经修订的协议、规则一经公布，立即自动生效。您应不时地注意本协议及附属规则地变更，若您不同意相关变更，本网站有权不经任何告知终止、中止本协议或者限制您进入本网站的全部或者部分板块且不承担任何法律责任。但该终止、中止或限制行为并不能豁免您在本网站已经进行的交易下所应承担的任何法律责任。\n您确认本注册协议后，本注册协议即在您和本网站之间产生法律效力。您按照本网站规定的注册程序成功注册为用户，您的行为即表示同意并签署了本注册协议。本注册协议不涉及您与本网站的其他用户之间因网上交易而产生的法律关系及法律纠纷，但您在此同意将全面接受和履行与本网站其他用户在本网站签订的任何电子法律文本，并承诺按该法律文本享有或放弃相应的权利、承担或豁免相应的义务。\n一、•使用规则\n本网站中的全部内容的版权均属于本网站所有，该等内容包括但不限于文本、数据、文章、设计、源代码、软件、图片、照片及其他全部信息(以下称“网站内容”)。网站内容受中华人民共和国著作权法及各国际版权公约的保护。未经本网站事先书面同意，您承诺不以任何方式、不以任何形式复制、模仿、传播、出版、公布、展示网站内容，包括但不限于电子的、机械的、复印的、录音录像的方式和形式等。您承认网站内容是属于本网站的财产。未经本网站书面同意，您亦不得将本网站包含的资料等任何内容镜像到任何其他网站或者服务器。任何未经授权对网站内容的使用均属于违法行为，本网站将追究您的法律责任。\n您承诺合法使用本网站提供的服务及网站内容。禁止在本网站从事任何可能违反中国现行的法律、法规、规章和政府规范性文件的行为或者任何未经授权使用本网站的行为，如擅自进入本网站的未公开的系统、不正当的使用密码和网站的任何内容等。\n本网站接受中国大陆(不包括香港特区、澳门特区及台湾地区)的18周岁以上的具有完全民事行为能力的自然人成为网站用户。如不具备上述资格，您应立即停止在本网站的注册程序、停止使用本网站服务，本网站有权随时终止您的注册进程及本网站服务，您应对您的注册给本网站带来的损失承担全额赔偿责任，且您的监护人（如您为限制民事行为能力的自然人）或您的实际控制人应承担连带责任。在注册时和使用本网站服务的所有期间，您应提供您自身的真实资料和信息，并保证自您注册之时起至您使用本网站服务的所有期间，其所提交的所有资料和信息（包括但不限于电子邮件地址、联系电话、联系地址、邮政编码、个人身份信息、征信信息等）真实、准确、完整，且是最新的。\n您注册成功后，不得将本网站的用户名转让给第三方或者授权给第三方使用。您确认，您用您的用户名和密码登陆本网站后在本网站的一切行为均代表您并由您承担相应的法律后果。\n经国家生效法律文书或行政处罚决定确认您存在违法行为，或者本网站有足够事实依据可以认定您存在违法或违反本服务协议的行为的或者您借款逾期未还的，本网站有权在因特网络上公布您的违法、违约行为，并有关将该内容记入任何与您相关的信用资料和档案。\n二、•风险提示\n您了解并认可，任何通过德成贷进行的交易并不能避免以下风险的产生，德成贷不能也没有义务为如下风险负责：\n1、 宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，用户有可能遭受损失；\n2、 政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，用户有可能遭受损失；\n3、利率风险：市场利率变化可能对购买或持有产品的实际收益产生影响；\n4、不可抗力因素导致的风险；\n5、因您的过错导致的任何损失，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码被他人破解、您使用的计算机系统被第三方侵入、您委托他人代理交易时他人恶意或不当操作而造成的损失。\n以上并不能揭示您通过德成贷进行交易的全部风险及市场的全部情形。您在做出交易决策前，应全面了解相关交易，依赖于您的独立判断进行交易，谨慎决策，并自行承担全部风险。\n三、•德成贷提供的服务项目\n（一）“德成贷账户”：指在您使用本服务时，本网站向您提供的唯一的虚拟的电子账户。您可自行设置密码，并用以查询或计量您的应收或应付款等数据信息。\n（二）德成贷向您提供的款项代管代收的中介服务，其中包含：\n1、充值：您可以通过银行将资金转账到德成贷指定的第三方支付账户。充值完成后，德成贷将在您的的电子账户内增加等额资金数。\n2、代管：您可以使用本服务指定的方式向您的德成贷账户充值，并委托德成贷代为保管。\n3、代收：您可以要求德成贷代为收取其他德成贷用户向您支付的款项。\n4、提现：您可以要求德成贷退返德成贷代管的您的款项（即退返）或向您支付您的应收款（即提现）。当您向德成贷做出提现指令时，必须提供一个与您委托德成贷代管时的汇款人或您的名称相符的有效的中国大陆银行账户，德成贷将于收到指令后的一至五个工作日内，将相应的款项汇入您提供的有效银行账户（根据您提供的银行不同，会产生汇入时间上的差异）。除本条约定外，德成贷不接受您提供的其他受领方式。\n5、查询：德成贷将对您在本系统中的所有操作进行记录，不论该操作之目的最终是否实现。您可以在本系统中实时查询您的账户名下的交易记录，您认为记录有误的，德成贷将向您提供德成贷已按照您的指令所执行的收付款的记录。您理解并同意您最终收到款项的服务是由您提供的银行账户对应的银行提供的，您需向该银行请求查证。\n6、款项专属：对您支付到德成贷并归属至您账户的款项及您通过账户收到的款项，德成贷将予以妥善保管。\n（三）德成贷并非银行或其它金融机构，本服务也非金融业务，本协议项下的资金移转均通过第三方来实现，你理解并同意您的资金于流转途中的合理时间。\n（四）本服务所涉及到的任何款项只以人民币计结，不提供任何形式的结售汇业务。\n（五）发标：您注册成为本网站借款用户后，可以委托德成贷将其借款需求信息通过本网站公开发布，即发出借款要约。\n（六）投标：您注册成为本网站投资用户后，可以委托德成贷在本网站发布的借款需求信息的借款用户做出借款承诺，并委托德成贷将甲方电子账户内的资金额划分付到借款用户的电子账户中。\n四、•涉及第三方网站\n本网站的网站内容可能涉及由第三方所有、控制或者运营的其他网站(以下称“第三方网站”)。本网站不能保证也没有义务保证第三方网站上的信息的真实性和有效性。您确认按照第三方网站的服务协议使用第三方网站，而不是按照本协议。第三方网站不是本网站推荐或者介绍的，第三方网站的内容、产品、广告和其他任何信息均由您自行判断并承担风险，而与本网站无关。\n五、责任限制\n在任何情况下，本网站对您使用本网站服务而产生的任何形式的的直接或间接损失均不承担法律责任，包括但不限于资金损失、利润损失、营业中断损失等。因为本网站或者涉及的第三方网站的设备、系统存在缺陷或者因为计算机病毒造成的损失，本网站均不负责赔偿，您的补救措施只能是与本网站终止本协议并停止使用本网站。但是，中国现行法律、法规另有规定的除外。\n六、网站内容监测\n本网站没有义务监测网站内容，但是您确认并同意本网站有权不时地根据法律、法规、政府要求透露、修改或者删除必要的、合适的信息，以便更好地运营本网站并保护自身及本网站的其他合法用户。\n七、个人信息的使用\n本网站对于收集到的经认证的个人信息将按照本网站的隐私规则予以保护。\n八、补偿\n就任何第三方提出的，由于您违反本协议及纳入本协议的条款和规则或您违反任何法律、法规或侵害第三方的权利而产生或引起的每一种类和性质的任何索赔、要求、诉讼、损失和损害(实际的、特别的及有后果的)，无论是已知或未知的，包括合理的律师费，您同意就此对本网站和(如适用)本网站的母公司、子公司、关联公司、高级职员、董事、代理人和雇员进行补偿并使其免受损害。\n九、服务的中止（即有期限停止）、终止\n除非本网站终止本协议或者您申请终止本协议且经本网站同意，否则本协议始终有效。本网站有权在不通知您的情况下在任何时间终止本协议或者限制您使用本网站。\n1、发生下列情形之一时，德成贷有权随时中止或终止向用户提供的服务：\n(a) 对于网络设备进行必要的保养及施工时；\n(b) 出现突发性的网络设备故障时；\n(c) 由于德成贷所使用的网络通信设备由于不明原因停止，无法提供服务时；\n(d) 由于不可抗力因素致使德成贷无法提供线上服务时；\n(e) 应相关政府机构的要求。\n2、如发生下列任何一种情形德成贷有权随时中止或终止向用户提供本协议项下的服务而无需通知用户：\n(a) 用户提供的个人资料不准确，不真实，不合法有效；\n(b) 用户使用任何第三方程序进行登录或使用服务；\n(c) 用户滥用所享受的权利；\n(d) 用户有损害其他用户的行为；\n(e) 用户有违背社会风俗和社会道德的行为；\n(f) 用户有违反本协议中相关规定的行为。\n3、如因系统维护或升级的需要而需暂停网络服务，德成贷将尽可能事先进行通告。\n4、终止用户服务后，用户使用德成贷服务的权利立即终止。从终止用户服务即刻起，广东德成贷投资管理有限公司不再对用户承担任何责任和义务。\n十、适用法律和管辖\n因本网站提供服务所产生的争议均适用中华人民共和国法律，并由德成贷住所地人民法院管辖。\n十一、附加条款\n在本网站的某些部分或页面中可能存在除本协议以外的单独的附加服务条款，当这些条款存在冲突时，在该些部分和页面中附加条款优先适用。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_register_xieyi);
		super.init();
		setHeadTitle(true,"注册协议");

		webview = (WebView) findViewById(R.id.register_context);

		dohttp();

	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initEvent() {

	}

	/**
	 * 获取数据
	 */
	private void dohttp() {

		JsonBuilder builder = new JsonBuilder();
		BaseHttpClient.post(this, Default.GET_REGISTER_CONTEXT, builder, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				showLoadingToast();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				//Log.i("DD", "注册规则详情json----"+response.toString());
				hideLoadingToast();
				try {

					if (statusCode == 200) {

						if (response != null) {
							if (response.has("message")) {
								String html = "<html><head><style type='text/css'>p{text-left: center;border-style:" + " none;border-top-width: 2px;border-right-width: 2px;border-bottom-width: 2px;border-left-width: 2px;}" + "img{height:auto;width: auto;width:100%;}</style></head><body>" + response.getString("message")+ "</body></html>";

								webview.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
							}
						}

					}
				} catch (Exception e) {
					// TODO: handle exception
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				hideLoadingToast();
				String html = "<html><head><style type='text/css'>p{text-left: center;border-style:" + " none;border-top-width: 2px;border-right-width: 2px;border-bottom-width: 2px;border-left-width: 2px;}" + "img{height:auto;width: auto;width:100%;}</style></head><body>" + registerRule+ "</body></html>";
				webview.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
			}
		});

	}


}
