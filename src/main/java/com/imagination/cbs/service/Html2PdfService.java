package com.imagination.cbs.service;

import java.io.ByteArrayOutputStream;

import com.imagination.cbs.domain.BookingRevision;

/**
 * @author pravin.budage
 *
 */
public interface Html2PdfService {

	ByteArrayOutputStream generateAgreementPdf(BookingRevision revision);
}
