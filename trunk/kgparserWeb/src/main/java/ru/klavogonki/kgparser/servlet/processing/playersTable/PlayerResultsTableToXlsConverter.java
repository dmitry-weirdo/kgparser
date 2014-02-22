package ru.klavogonki.kgparser.servlet.processing.playersTable;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.klavogonki.kgparser.processing.playersTable.*;
import su.opencode.kefir.util.StringUtils;

import java.util.List;

import static su.opencode.kefir.util.StringUtils.concat;

/**
 * Copyright 2014 <a href="mailto:dmitry.weirdo@gmail.com">Dmitriy Popov</a>.
 * $HeadURL$
 * $Author$
 * $Revision$
 * $Date::                      $
 */
public class PlayerResultsTableToXlsConverter
{
	public static XSSFWorkbook toXssfWorkbook(PlayersResultsTable table) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("Таблица результатов игроков");
		fillCompetitionName(table, workbook, sheet);
		fillHeaderRows(table, workbook, sheet);
		fillPlayerRows(table, workbook, sheet);

		setColumnsWidths(table, sheet);

		return workbook;
	}

	private static void fillCompetitionName(PlayersResultsTable table, XSSFWorkbook workbook, XSSFSheet sheet) {
		// todo: use constants from POI
		int rowNum = 0;
		int colNum = 0;

		XSSFRow row = sheet.createRow(rowNum);

		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		XSSFCell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING);
		cell.setCellStyle(style);
		cell.setCellValue( table.getCompetitionName() );

		CellRangeAddress address = new CellRangeAddress(rowNum, rowNum, colNum, colNum + 3);
		sheet.addMergedRegion(address);
	}
	private static void fillHeaderRows(PlayersResultsTable table, XSSFWorkbook workbook, XSSFSheet sheet) {
		int startRow = 1; // row for competition name

		List<HeaderRow> headerRows = table.getHeaderRows();
		for (int i = 0; i < headerRows.size(); i++)
		{
			int rowNum = i + startRow;

			HeaderRow headerRow = headerRows.get(i);
			XSSFRow row = sheet.createRow(rowNum);

			List<HeaderCell> cells = headerRow.getCells();
			int colNum = 0;
			for (HeaderCell cell : cells)
			{
				Font font = workbook.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);

				CellStyle style = workbook.createCellStyle();
				style.setFont(font);
				style.setAlignment(CellStyle.ALIGN_CENTER);

				XSSFCell xssfCell = row.createCell(colNum, Cell.CELL_TYPE_STRING);
				xssfCell.setCellStyle(style);

				if (cell.hasColSpan())
				{
					CellRangeAddress address = new CellRangeAddress(rowNum, rowNum, colNum, colNum + (cell.getColSpan() - 1));
					sheet.addMergedRegion(address);

					colNum += (cell.getColSpan() - 1);
				}

				if (StringUtils.notEmpty(cell.getText()))
				{
					xssfCell.setCellValue(cell.getText());
				}

				colNum++;
			}
		}
	}
	private static void fillPlayerRows(PlayersResultsTable table, XSSFWorkbook workbook, XSSFSheet sheet) {
		int startRow = 1 + table.getHeaderRows().size(); // row for competition name and header rows

		List<PlayerRow> playersRows = table.getPlayersRows();
		for (int i = 0; i < playersRows.size(); i++)
		{
			int rowNum = i + startRow;

			PlayerRow playerRow = playersRows.get(i);
			XSSFRow row = sheet.createRow(rowNum);

			List<PlayerCell> cells = playerRow.getCells();
			int colNum = 0;
			for (PlayerCell cell : cells)
			{
//				CellStyle style = workbook.createCellStyle();
//				style.setFont(font);
//				style.setAlignment(CellStyle.ALIGN_CENTER);

				XSSFCell xssfCell = row.createCell(colNum, Cell.CELL_TYPE_STRING); // todo: set type according to cell field
//				xssfCell.setCellStyle(style);

				if (cell.hasColSpan())
				{
					CellRangeAddress address = new CellRangeAddress(rowNum, rowNum, colNum, colNum + (cell.getColSpan() - 1));
					sheet.addMergedRegion(address);

					colNum += (cell.getColSpan() - 1);
				}

				if (StringUtils.notEmpty(cell.getText()))
				{
					xssfCell.setCellValue(cell.getText());
				}

				colNum++;
			}
		}
	}

	private static void setColumnsWidths(PlayersResultsTable table, XSSFSheet sheet) {
		int columnsCount = getColumnsCount(table);
		logger.info( concat("columns count: ", columnsCount) );
		for (int i = 0; i < columnsCount; i++)
		{
			sheet.setColumnWidth(i, 256 * 20);
//					sheet.autoSizeColumn(i, true); // does not work, sets columns witdh to 0 and hidden
		}
	}
	private static int getColumnsCount(PlayersResultsTable table) {
		int columnsCount = 0;
		HeaderRow firstHeaderRow = table.getHeaderRows().get(0);
		for (HeaderCell cell : firstHeaderRow.getCells())
		{
			if (cell.hasColSpan())
				columnsCount += cell.getColSpan();
			else
				columnsCount += 1;
		}
		return columnsCount;
	}

	private static final Logger logger = Logger.getLogger(PlayerResultsTableToXlsConverter.class);
}