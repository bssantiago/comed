package com.mhc.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.context.MessageSource;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.StudyResultDTO;
import com.mhc.services.ApplicationContextProvider;

public class PdfUtils {
	private static final Logger LOG = Logger.getLogger(PdfUtils.class);
	private static PDFont font = PDType1Font.HELVETICA;
	private static float fonSize = 10;
	private static String path = "/images/";
	private static MessageSource messageSource = (MessageSource) ApplicationContextProvider.getApplicationContext()
			.getBean("messageSource");

	public File PdfGenerator(ParticipantsDTO participant, List<StudyResultDTO> studies) throws IOException {

		File result = new File(
				messageSource.getMessage(Constants.TMP_FOLDER, null, null) + participant.getId() + "HealthLetter.pdf");
		PDDocument doc = new PDDocument();
		try {
			float marginTopStart = 720;
			float marginBig = 30;
			float marginMidium = 20;
			float marginLittle = 15;
			float currentMargin = marginTopStart;
			float leftMargin = 20;

			PDPage page = new PDPage(PDRectangle.A4);
			doc.addPage(page);
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			contentStream.setFont(font, fonSize);
			PDImageXObject pdImage = PDImageXObject.createFromFile(getPathResource("Header2.png"), doc);
			contentStream.drawImage(pdImage, 0, 810);

			PDImageXObject pdLogo = PDImageXObject.createFromFile(getPathResource("Logo.png"), doc);
			contentStream.drawImage(pdLogo, 15, 760);

			contentStream.setNonStrokingColor(Color.black);
			Date currentDate = new Date();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String reportDate = df.format(currentDate);
			Date dob = participant.getDate_of_birth();
			Date creationDate = participant.getCreation_date();
			String dobString = dob == null ? "Unkonw" : df.format(dob);
			String creationDateString = creationDate == null ? reportDate : df.format(creationDate);

			this.pdfWrite(contentStream, leftMargin, currentMargin, reportDate); // 750
			currentMargin = currentMargin - marginBig;

			Object[] args = new Object[] { participant.getFirst_name(), participant.getLast_name(), creationDateString,
					dobString, participant.getPrimary_care() };

			String docLetter = messageSource.getMessage("doctor.letter.text", args, null);
			String[] wrapText = docLetter.split("\r\n");
			for (String text : wrapText) {
				this.pdfWrite(contentStream, leftMargin, currentMargin, text);
				currentMargin = currentMargin - marginMidium;
			}

			try {
				currentMargin = currentMargin - marginMidium;
				currentMargin = this.drawTable(doc, page, contentStream, currentMargin, leftMargin, studies);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			currentMargin = currentMargin - marginLittle;
			String docFooter = messageSource.getMessage("doctor.letter.footer", null, null);
			wrapText = docFooter.split("\r\n");
			for (String text : wrapText) {
				this.pdfWrite(contentStream, leftMargin, currentMargin, text);
				currentMargin = currentMargin - marginMidium;
			}
			contentStream.close();
			doc.save(result);

			return result;
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
	}

	private String getPathResource(String resourceName) {
		return PdfUtils.class.getResource(path + resourceName).getPath();
	}

	private void pdfWrite(PDPageContentStream contentStream, float left, float margin, String text) {
		try {
			contentStream.beginText();
			contentStream.newLineAtOffset(left, margin);
			contentStream.showText(text);
			contentStream.endText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private float StringLenght(String text) {
		float result = 0;
		try {
			result = font.getStringWidth(text) / 1000 * fonSize;
		} catch (IOException e) {
			LOG.equals(e);
		}
		return result;
	}

	private float drawTable(PDDocument doc, PDPage page, PDPageContentStream contentStream, float y, float margin,
			List<StudyResultDTO> studies) throws IOException {

		final int rows = studies.size() + 1;
		final int cols = 3;
		final float rowHeight = 20f;
		final float tableWidth = page.getCropBox().getWidth() - margin - margin;
		final float tableHeight = rowHeight * rows;
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		try {
			float nexty = y;
			// now add the text
			float textx = margin + cellMargin;
			float texty = y - 15;

			PDImageXObject pdGridHeader = PDImageXObject.createFromFile(getPathResource("GridHeader0.png"), doc);
			contentStream.drawImage(pdGridHeader, 20, texty - 5);
			contentStream.setNonStrokingColor(Color.white);

			this.pdfWrite(contentStream, (colWidth + margin + cellMargin - this.StringLenght("Measure")) / 2, texty,
					"Measure");
			textx += colWidth;

			contentStream.drawImage(pdGridHeader, colWidth + 20, texty - 5);

			this.pdfWrite(contentStream, (textx + colWidth + colWidth - this.StringLenght("Desirable")) / 2, texty,
					"Desirable");
			textx += colWidth;

			contentStream.drawImage(pdGridHeader, colWidth + colWidth + 20, texty - 5);

			this.pdfWrite(contentStream, (textx + colWidth + colWidth + colWidth - this.StringLenght("Result")) / 2,
					texty, "Result");
			textx += colWidth;

			texty -= rowHeight;
			textx = margin + cellMargin;

			contentStream.setNonStrokingColor(Color.black);

			int count = 1;
			for (final StudyResultDTO item : studies) {
				if ((count & 1) == 0) {
					PDImageXObject pdGridDatos1 = PDImageXObject.createFromFile(getPathResource("GridDatos.png"), doc);
					contentStream.drawImage(pdGridDatos1, 20, texty - 5);
					contentStream.drawImage(pdGridDatos1, colWidth + 20, texty - 5);
					contentStream.drawImage(pdGridDatos1, colWidth + colWidth + 20, texty - 5);
				}

				String text1 = item.getMeasure();
				this.pdfWrite(contentStream, (textx + colWidth - this.StringLenght(text1)) / 2, texty, text1);
				textx += colWidth;

				String text2 = item.getDesirable();

				this.pdfWrite(contentStream, (textx + colWidth + colWidth - this.StringLenght(text2)) / 2, texty,
						text2);
				textx += colWidth;

				String text3 = item.getResult();
				this.pdfWrite(contentStream, (textx + colWidth + colWidth + colWidth - this.StringLenght(text3)) / 2,
						texty, text3);
				textx += colWidth;

				texty -= rowHeight;
				textx = margin + cellMargin;
				count++;
			}

			nexty = y;
			for (int i = 0; i <= rows; i++) {
				drawLine(contentStream, margin, nexty, margin + tableWidth, nexty);
				nexty -= rowHeight;
			}

			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				drawLine(contentStream, nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			return texty;
		} catch (Exception ex) {
			throw ex;
		}
		// draw the rows

	}

	private void drawLine(PDPageContentStream content, float xstart, float ystart, float xend, float yend) {
		try {
			content.moveTo(xstart, ystart);
			content.lineTo(xend, yend);
			content.stroke();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
