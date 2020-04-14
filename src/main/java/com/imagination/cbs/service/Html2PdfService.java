package com.imagination.cbs.service;

import java.io.OutputStream;

import com.imagination.cbs.domain.BookingRevision;

/**
 * @author pravin.budage
 *
 */
public interface Html2PdfService {

	OutputStream generateAgreementPdf(BookingRevision revision);

}
