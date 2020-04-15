package com.imagination.cbs.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.imagination.cbs.domain.BookingRevision;
import com.imagination.cbs.domain.BookingWorkTask;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.service.Html2PdfService;
import com.itextpdf.html2pdf.HtmlConverter;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author pravin.budage
 *
 */
@Service("html2PdfService")
public class Html2PdfServiceImpl implements Html2PdfService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Html2PdfServiceImpl.class);

	@Autowired
	private Configuration config;

	private static final String TEMPLATE_NAME = "pdf.agreement.ftl";

	private static final String SIGN_IMG = "templates/signature.png";

	private static final String IMAGINATION_LOGO_IMG = "templates/imagination_logo.png";

	@Override
	public ByteArrayOutputStream generateAgreementPdf(BookingRevision revision) {
		Map<String, Object> data = prepareInputToPdf(revision);

		try {

			Template template = config.getTemplate(TEMPLATE_NAME);

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			HtmlConverter.convertToPdf(html, stream);

			return stream;

		} catch (Exception e) {
			LOGGER.error("Failed to generate PDF");
		}
		return null;

	}

	private Map<String, Object> prepareInputToPdf(BookingRevision latestRevision) {

		Map<String, Object> dataModel = new HashMap<>();

		String address1 = latestRevision.getContractor().getAddressLine1();
		String address2 = latestRevision.getContractor().getAddresLine2();
		String address3 = latestRevision.getContractor().getAddresLine3();
		String postalCode = latestRevision.getContractor().getPostalCode();
		String postalDistrict = latestRevision.getContractor().getPostalDistrict();
		String country = latestRevision.getContractor().getCountry();
		RoleDm role = latestRevision.getRole();

		List<BookingWorkTask> bookingWorkTasks = latestRevision.getBookingWorkTasks();

		ClassLoader loader = this.getClass().getClassLoader();
		File imaginationLogoFile = new File(loader.getResource(IMAGINATION_LOGO_IMG).getFile());
		byte[] imgBytesAsBase64Logo = null;

		File signatureFile = new File(loader.getResource(SIGN_IMG).getFile());
		byte[] imgBytesAsBase64Sign = null;
		try {
			imgBytesAsBase64Logo = Base64.encodeBase64(Files.readAllBytes(imaginationLogoFile.toPath()));
			imgBytesAsBase64Sign = Base64.encodeBase64(Files.readAllBytes(signatureFile.toPath()));
		} catch (IOException e) {
			LOGGER.error("Failed to read bytes of Image");
		}
		String imgDataAsBase64Logo = new String(imgBytesAsBase64Logo);
		String imgAsBase64Logo = "data:image/png;base64," + imgDataAsBase64Logo;

		String imgDataAsBase64Sign = new String(imgBytesAsBase64Sign);
		String imgAsBase64Sign = "data:image/png;base64," + imgDataAsBase64Sign;

		StringBuilder builder = new StringBuilder();

		if (bookingWorkTasks != null) {
			for (BookingWorkTask bookingWorkTask : bookingWorkTasks) {
				builder.append(bookingWorkTask.getTaskId() + "\n");
				builder.append(bookingWorkTask.getTaskName() + "\n");
				builder.append(bookingWorkTask.getTaskDeliveryDate() + "\n");
				builder.append(bookingWorkTask.getTaskDateRate() + "\n");
				builder.append(bookingWorkTask.getTaskTotalAmount() + "\n");
				builder.append(bookingWorkTask.getTaskTotalDays() + "\n");
			}
		}

		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String dateString = dateFormat.format(new Date());

		dataModel.put("consultancyCompanyName", latestRevision.getContractor().getContractorName());
		dataModel.put("consultancyCompanyNumber", latestRevision.getContractor().getMaconomyVendorNumber());
		dataModel.put("consultancyCompanyAddress", address1 + ", " + address2 + ", " + address3 + ", " + postalCode
				+ ", " + postalDistrict + ", " + country);
		dataModel.put("commencementDate", latestRevision.getContractedFromDate());
		dataModel.put("endDate", latestRevision.getContractedToDate());
		dataModel.put("services", role.getRoleName() + ", " + role.getDiscipline().getDisciplineName());
		dataModel.put("companyProject", latestRevision.getJobname());
		dataModel.put("confirmationOfServiceNumber", latestRevision.getJobNumber());
		dataModel.put("representative", latestRevision.getContractor().getAttention());
		dataModel.put("consultancyEmailAddress", latestRevision.getContractor().getEmail());
		dataModel.put("engagingManager", latestRevision.getChangedBy());
		dataModel.put("fee", latestRevision.getContractAmountAftertax());
		dataModel.put("invoiceMilestones", builder.toString());
		dataModel.put("cestTestOutput", Boolean.valueOf(role.getInsideIr35()) ? "INSIDE" : "OUTSIDE");
		dataModel.put("signedBy", latestRevision.getContractor().getAttention());
		dataModel.put("signedDate", " ");
		dataModel.put("d1", dateString.charAt(0));
		dataModel.put("d2", dateString.charAt(1));
		dataModel.put("m1", dateString.charAt(2));
		dataModel.put("m2", dateString.charAt(3));
		dataModel.put("y1", dateString.charAt(4));
		dataModel.put("y2", dateString.charAt(5));
		dataModel.put("logo", imgAsBase64Logo);
		dataModel.put("signature", imgAsBase64Sign);

		return dataModel;
	}
}
