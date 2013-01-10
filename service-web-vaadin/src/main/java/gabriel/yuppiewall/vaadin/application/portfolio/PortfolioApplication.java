package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.vaadin.application.Application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLine;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLineEntry;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class PortfolioApplication implements Application<ComponentContainer>,
		Serializable {

	public PortfolioApplication() {

	}

	public boolean initialize;
	private VerticalLayout applicationUI;
	@Autowired
	private TransactionViewImpl contentPaneSearchSection;
	@Autowired
	private PortfolioTreeViewImpl portfolioTreeView;

	@Override
	public void initialize() {
		// setMargin(true);
		// setSpacing(true);
		// make it fill the whole window
		if (initialize)
			return;
		applicationUI = new VerticalLayout();
		applicationUI.setSizeFull();
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

		applicationUI.addComponent(hsp);
		VerticalSplitPanel navBar = new VerticalSplitPanel();
		hsp.setFirstComponent(navBar);

		portfolioTreeView.init();
		navBar.setFirstComponent(portfolioTreeView.getRoot());
		navBar.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

		{// RSS feed reader
			VerticalLayout readerLayout = new VerticalLayout();
			navBar.setSecondComponent(readerLayout);

			readerLayout.setSpacing(true);
			// readerLayout.setMargin(true);
			{
				Panel p = new Panel(
						"Independent auditor given more time to submit Satyam report - Livemint");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNGUmI-WhMB_O1zdDb7E6F-awT3fgA&amp;url=http://www.livemint.com/Companies/VLd6VrvqJ2CLX3vPSLkRjN/Independent-auditor-given-more-time-to-submit-Satyam-report.html\"><img src=\"//nt3.ggpht.com/news/tbn/Z0kinumtF7-7RM/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">Livemint</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNGUmI-WhMB_O1zdDb7E6F-awT3fgA&amp;url=http://www.livemint.com/Companies/VLd6VrvqJ2CLX3vPSLkRjN/Independent-auditor-given-more-time-to-submit-Satyam-report.html\"><b>Independent auditor given more time to submit <b>Satyam</b> report</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Livemint</font></b></font><br /><font size=\"-1\">Hyderabad: Brahmaiah and Co., the independent auditor designated by the Andhra Pradesh high court to scrutinize the scheme of amalgamation of information technology (IT) services providers Mahindra <b>Satyam</b> and parent Tech Mahindra Ltd, has been <b>...</b></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNF9yP0IiLj0TlDfXGXJl0qhRpUtBQ&amp;url=http://www.pardaphash.com/news/opportunity-for-engineering-freshers-as-mahindra-satyam-to-recruit-1500-college-graduates/702433.html\">Mahindra <b>Satyam</b> to increase headcounts</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Parda Phash</nobr></font></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNGqcEhRdlT5jHgEjntyy7q5ACGicA&amp;url=http://www.thehindubusinessline.com/industry-and-economy/info-tech/brahmaiah-to-submit-report-on-mahindra-satyamtech-m-accounts/article4273537.ece?ref%3Dwl_industry-and-economy\">Brahmaiah to submit report on Mahindra <b>Satyam</b>-Tech M accounts</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Hindu Business Line</nobr></font></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=d1gxPwqzH2WDs6MdvZNL276Y_I2UM&amp;ned=us\"><nobr><b>all 4 news articles&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			// readerLayout.setMargin(true);
			{
				Panel p = new Panel(
						"Ex-directors of Satyam win ruling in US class action suit - Thomson Reuters News & Insight");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFgR9hoefnewAJl01w1mdXA5xC9BA&amp;url=http://www.moneycontrol.com/news/business/us-court-dismisses-claims-against-former-satyam-directors_802934.html\"><img src=\"//nt0.ggpht.com/news/tbn/AI9tIwWOp6fPBM/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">Moneycontrol.com</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNGrkRQssYphZQ97ZyNoWewSj5GbOA&amp;url=http://newsandinsight.thomsonreuters.com/Legal/News/2013/01_-_January/Ex-directors_of_Satyam_win_ruling_in_U_S__class_action_suit/\"><b>Ex-directors of <b>Satyam</b> win ruling in US class action suit</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Thomson Reuters News & Insight</font></b></font><br /><font size=\"-1\">NEW YORK, Jan 2 (Reuters) - A U.S. federal judge dismissed claims against seven former directors of <b>Satyam</b> Computer Services Ltd in shareholder lawsuits stemming from the massive fraud at the heart of India&#39;s largest corporate scandal. U.S. District <b>...</b></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFwrgVGH63-jj5BlqVDjqFh4IrbMg&amp;url=http://timesofindia.indiatimes.com/business/india-business/US-court-dismisses-claims-against-ex-Satyam-directors/articleshow/17880010.cms\">US court dismisses claims against ex-<b>Satyam</b> directors</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Times of India</nobr></font></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNGuF3hWGPXTZIgq6ad85-JuClAqKA&amp;url=http://www.business-standard.com/india/news/us-court-dismisses-claims-against-satyam-computer-ex-directors/497703/\">US court dismisses claims against <b>Satyam</b> Computer ex-directors</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Business Standard</nobr></font></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNG-AUdlkkyCeIHB8nQnSGAUK9-5jw&amp;url=http://www.bloomberg.com/news/2013-01-02/former-satyam-directors-win-dismissal-of-u-s-fraud-lawsuit-1-.html\">Ex-<b>Satyam</b> Directors Win Dismissal of US Fraud Lawsuit</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Bloomberg</nobr></font></font><br /><font size=\"-1\" class=\"p\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNEfqYEPwY1QSce33JQePHhFSVvL5Q&amp;url=http://www.thehindubusinessline.com/industry-and-economy/info-tech/charges-dismissed-against-former-satyam-directors/article4268576.ece?ref%3Dwl_industry-and-economy\"><nobr>Hindu Business Line</nobr></a></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dpA8IRdxhDuV-TMocraotqaHnC_AM&amp;ned=us\"><nobr><b>all 58 news articles&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"US court dismisses claims against former Satyam directors - The Hindu");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFgkBk6WhuufYH4-rCqHTy5zLGt7w&amp;url=http://www.thehindu.com/business/companies/us-court-dismisses-claims-against-former-satyam-directors/article4268546.ece\"><img src=\"//nt1.ggpht.com/news/tbn/IVHSwCNFhL1U6M/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">The Hindu</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFgkBk6WhuufYH4-rCqHTy5zLGt7w&amp;url=http://www.thehindu.com/business/companies/us-court-dismisses-claims-against-former-satyam-directors/article4268546.ece\"><b>US court dismisses claims against former <b>Satyam</b> directors</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">The Hindu</font></b></font><br /><font size=\"-1\">A U.S. court has dismissed civil claims filed against former independent directors of erstwhile <b>Satyam</b> Computer, saying they themselves were victims of over $ 1-billion accounting fraud at Indian IT firm. Hearing the petition, the US District Court <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dgvXxfHpcquWjsM&amp;ned=us\"><nobr><b></b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Mid-tier IT cos like Mahindra Satyam and Mastek may cut pay hikes, hiring on ... - Economic Times");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFj7xvokUAqGioBf80hAxq_694XAg&amp;url=http://economictimes.indiatimes.com/tech/ites/mid-tier-it-cos-like-mahindra-satyam-and-mastek-may-cut-pay-hikes-hiring-on-us-europe-woes/articleshow/17863998.cms\"><img src=\"//nt0.ggpht.com/news/tbn/kNJf50lriwYN2M/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">Economic Times</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFj7xvokUAqGioBf80hAxq_694XAg&amp;url=http://economictimes.indiatimes.com/tech/ites/mid-tier-it-cos-like-mahindra-satyam-and-mastek-may-cut-pay-hikes-hiring-on-us-europe-woes/articleshow/17863998.cms\"><b>Mid-tier IT cos like Mahindra <b>Satyam</b> and Mastek may cut pay hikes, hiring on <b>...</b></b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Economic Times</font></b></font><br /><font size=\"-1\">Coming out of a slow year when the IT industry saw slowing revenue growth thanks to clients being more cautious about technology spending, software services exporters such as Infinite Technologies, Mastek, Mahindra <b>Satyam</b> and Infotech Enterprises said <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dHhev7Q9TDa2zhM&amp;ned=us\"><nobr><b></b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Mahindra Satyam can hope for World Bank projects again - Hindu Business Line");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNH6FPXHz-kVVAyhjynRUnV_bw6M0A&amp;url=http://www.thehindubusinessline.com/industry-and-economy/info-tech/mahindra-satyam-can-hope-for-world-bank-projects-again/article3335435.ece\"><b>Mahindra <b>Satyam</b> can hope for World Bank projects again</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Hindu Business Line</font></b></font><br /><font size=\"-1\">Mahindra <b>Satyam</b> hopes to bid for World Bank projects post merger with Tech Mahindra. The company was barred by World Bank in October 2008 on data theft allegations. &quot;Yes. That is one of the benefits we see in the merger. After the merger, we can put <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dVqq6z_jR8FDkZM&amp;ned=us\"><nobr><b>and more&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Accounting News Roundup: Later, 112th; Judge Gives Ex-Satyam Audit ... - Going Concern");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNG18qQhYL9e86FG4YFdyBRSrBMh1w&amp;url=http://goingconcern.com/post/accounting-news-roundup-later-112th-judge-gives-ex-satyam-audit-committee-members-break-what\"><img src=\"//nt2.ggpht.com/news/tbn/6uZc45Is-sLeaM/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">Going Concern</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNG18qQhYL9e86FG4YFdyBRSrBMh1w&amp;url=http://goingconcern.com/post/accounting-news-roundup-later-112th-judge-gives-ex-satyam-audit-committee-members-break-what\"><b>Accounting News Roundup: Later, 112th; Judge Gives Ex-<b>Satyam</b> Audit <b>...</b></b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Going Concern</font></b></font><br /><font size=\"-1\">A U.S. federal judge dismissed claims against seven former directors of <b>Satyam</b> Computer Services Ltd (SATY.NS) in shareholder lawsuits stemming from the massive fraud at the heart of India&#39;s largest corporate scandal. U.S. District Judge Barbara Jones <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dkziO6ybgA0U49M&amp;ned=us\"><nobr><b></b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"US court throws out suit against Satyam directors - domain-B");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFySOjGvuaLAdfm8xYpvJR6k_JEWQ&amp;url=http://www.domain-b.com/companies/companies_m/mahindra_satyam/20130103_satyam_computer.html\"><img src=\"//nt1.ggpht.com/news/tbn/1be4krUHrVR19M/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">domain-B</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFySOjGvuaLAdfm8xYpvJR6k_JEWQ&amp;url=http://www.domain-b.com/companies/companies_m/mahindra_satyam/20130103_satyam_computer.html\"><b>US court throws out suit against <b>Satyam</b> directors</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">domain-B</font></b></font><br /><font size=\"-1\">A US federal judge on Wednesday dismissed a civil lawsuit against seven former directors of the now defunct <b>Satyam</b> Computer Services, citing insufficient allegations in the complaint. The ruling in the class-action, or group, lawsuit involved only the <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dqbVWuPeADpwGkM&amp;ned=us\"><nobr><b></b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Mahindra Satyam - Cancellation of Stock Options - Moneycontrol.com");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNHopGeYme7B3vs6hWEdVVEOx0-nVw&amp;url=http://www.moneycontrol.com/stocks/stock_market/corp_notices.php?autono%3D627358\"><b>Mahindra <b>Satyam</b> - Cancellation of Stock Options</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Moneycontrol.com</font></b></font><br /><font size=\"-1\"><b>Satyam</b> Computer Services Ltd has informed BSE that the Company has cancelled following options granted under different schemes. 1. Name of the Scheme : ASOP - B No. of Options : 852016 2. Name of the Scheme : RSU - Equity No. of Options : 140157 <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dVlOrn7WkjfDFGM&amp;ned=us\"><nobr><b></b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Satyam to pay US$68 million to settle fraud claims - CSO");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNErm-cAZV1cx6kdSa_SwZ0ckRge4g&amp;url=http://www.csoonline.com/article/724231/satyam-to-pay-us-68-million-to-settle-fraud-claims\"><b><b>Satyam</b> to pay US$68 million to settle fraud claims</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">CSO</font></b></font><br /><font size=\"-1\">In a Bombay Stock Exchange (BSE) filing, Mahindra <b>Satyam</b> said that it signed a confidential settlement agreement to settle these claims made by those who are said to have lost about US$298 million. Mahindra <b>Satyam</b> has been able to fully and finally <b>...</b></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dcCW_0uJfl-C6yM&amp;ned=us\"><nobr><b>and more&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}

			{
				Panel p = new Panel(
						"Sebi penalizes Satyam executive for insider trading - Livemint");
				VerticalLayout layout = (VerticalLayout) p.getContent();
				layout.setMargin(true); // we want a margin
				readerLayout.addComponent(p);

				Label l = new Label(
						"<table border=\"0\" cellpadding=\"2\" cellspacing=\"7\" style=\"vertical-align:top;\"><tr><td width=\"80\" align=\"center\" valign=\"top\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNG9GwCnbGHhQ34DmuZehl0cuy1Ihg&amp;url=http://www.livemint.com/Companies/nQBJP3LH7cqg3WwbF16M2J/Sebi-penalizes-Satyam-executive-for-insider-trading.html\"><img src=\"//nt0.ggpht.com/news/tbn/QJXwPWYmkkhERM/6.jpg\" alt=\"\" border=\"1\" width=\"80\" height=\"80\" /><br /><font size=\"-2\">Livemint</font></a></font></td><td valign=\"top\" class=\"j\"><font style=\"font-size:85%;font-family:arial,sans-serif\"><br /><div style=\"padding-top:0.8em;\"><img alt=\"\" height=\"1\" width=\"1\" /></div><div class=\"lh\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNG9GwCnbGHhQ34DmuZehl0cuy1Ihg&amp;url=http://www.livemint.com/Companies/nQBJP3LH7cqg3WwbF16M2J/Sebi-penalizes-Satyam-executive-for-insider-trading.html\"><b>Sebi penalizes <b>Satyam</b> executive for insider trading</b></a><br /><font size=\"-1\"><b><font color=\"#6f6f6f\">Livemint</font></b></font><br /><font size=\"-1\">Mumbai: The Securities and Exchange Board of India (Sebi) on Friday imposed a penalty of Rs.65 lakh on T.A.N. Murti, former head of investor relations at <b>Satyam</b> Computer Services Ltd, for insider trading in the company&#39;s shares in 2008. According to <b>...</b></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFW4gOS3-JDaFRwDx1cSEQUvlCXyw&amp;url=http://www.moneycontrol.com/news/business/sebi-slaps-rs-65-lakh-fineofficialsatyam-computer_798510.html\">Sebi slaps Rs 65 lakh fine on official of <b>Satyam</b> Computer</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Moneycontrol.com</nobr></font></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNE_ODrPzgi_MqPBnOhjqSDKzFm7Ag&amp;url=http://www.thehindubusinessline.com/features/investment-world/tech-mahindra-buy/article4229416.ece?ref%3Dwl_opinion\">Tech Mahindra: Buy</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Hindu Business Line</nobr></font></font><br /><font size=\"-1\"><a href=\"http://news.google.com/news/url?sa=t&amp;fd=R&amp;usg=AFQjCNFPZj7UAoKft1WAC4lrGxNpn5u7RA&amp;url=http://www.thehindubusinessline.com/markets/insidertrading-sebi-imposes-rs-65-lakh-penalty-on-satyam-computer-staff/article4226171.ece?ref%3Dwl_investment-world\">Insider-trading: SEBI imposes Rs 65 lakh penalty on <b>Satyam</b> Computer staff</a><font size=\"-1\" color=\"#6f6f6f\"><nobr>Business Line</nobr></font></font><br /><font size=\"-1\" class=\"p\"></font><br /><font class=\"p\" size=\"-1\"><a class=\"p\" href=\"http://news.google.com/news/more?ncl=dRQutuuaXvniw3Msfe0jBq5OuEloM&amp;ned=us\"><nobr><b>all 14 news articles&nbsp;&raquo;</b></nobr></a></font></div></font></td></tr></table>");
				l.setContentMode(Label.CONTENT_XHTML);
				p.addComponent(l);
			}
		}

		VerticalSplitPanel contentPane = new VerticalSplitPanel();
		contentPane.setSplitPosition(70, Sizeable.UNITS_PERCENTAGE);
		hsp.setSecondComponent(contentPane);
		contentPaneSearchSection.init();
		contentPane.setFirstComponent(contentPaneSearchSection.getRoot());

		{
			AnnotatedTimeLine atl = new AnnotatedTimeLine();
			atl.setOption("displayAnnotations", true);
			// atl.setOption("wmode", "window");
			atl.setOption("wmode", "opaque");

			atl.addLineLabel("Sold Pencils");
			atl.addLineLabel("Sold Pens");
			// a time line can have multiple entries as above 'Sold Pencils' and
			// 'Sold Pens'
			// for each distinct entry you have to set a value for each of the
			// above entries

			ArrayList<AnnotatedTimeLineEntry> timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(30000, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(40645, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 1), timeLineEntries);

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(14045, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(20374, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 2), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(55022, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(50766, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 3), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(75284, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(14334,
					"Out of Stock", "Ran out of stock at 4pm")); // Sold Pens

			atl.add(new GregorianCalendar(2008, 0, 4), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
			timeLineEntries.add(new AnnotatedTimeLineEntry(41476,
					"Bought Pens", "Bought 200k Pens")); // Sold Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(66467, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 5), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
			timeLineEntries.add(new AnnotatedTimeLineEntry(33322,
					"Closed Shop", "Had enough of pencils business")); // Sold
																		// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(39463,
					"Pens look good", "Swapping to pens wholesale")); // Sold
																		// Pens

			atl.add(new GregorianCalendar(2008, 0, 6), timeLineEntries);

			atl.setSizeFull();
			contentPane.setSecondComponent(atl);
		}

		initialize = true;
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("getName");
	}

	@Override
	public boolean isInitialize() {
		return initialize;
	}

	@Override
	public ComponentContainer getApplicationUI() {
		return applicationUI;
	}

	@Override
	public String getTitle() {
		return "My Portfolio";
	}

	@Override
	public String getID() {
		throw new UnsupportedOperationException("getID");
	}

	@Override
	public String getThumbnail() {
		return "../wall-midnight/icons/portfolio.png";
	}
}
