package com.idy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.idy.base.BaseController;
import com.idy.base.EUIDataGridPage;
import com.idy.constant.LogTypeEnum;
import com.idy.domain.Colume;
import com.idy.domain.Excel;
import com.idy.domain.SheetLog;
import com.idy.service.ColumeService;
import com.idy.service.ExcelService;
import com.idy.service.SerObjService;
import com.idy.service.SheetLogService;
import com.idy.utils.ExcelUtils;

/**
 *@Description: 
 *@author pein
 *@date 2015年11月9日 下午4:01:51 
 *@version V1.0
 */
@Controller
@RequestMapping("/roster")
public class RosterController extends BaseController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RosterController.class);
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private ColumeService columeService;
	
	@Autowired
	private SerObjService serObjService;
	
	@Autowired
	private SheetLogService sheetLogService;

	public static Map<Integer, String> sheetMap = new HashMap<Integer, String>();
	
	public static Map<Integer, String> sheetPage = new HashMap<Integer, String>();
	
	static {
		//sheet名称
		sheetMap.put(1, "在职员工");
		sheetMap.put(2, "入职员工");
		sheetMap.put(3, "离职员工");
		sheetMap.put(4, "调入员工");
		sheetMap.put(5, "调出员工");
		sheetMap.put(6, "转正员工");
		sheetMap.put(7, "二次员工");
		
		//页面
		sheetPage.put(1, "serving");
		sheetPage.put(2, "entry");
		sheetPage.put(3, "leave");
		sheetPage.put(4, "callin");
		sheetPage.put(5, "callout");
		sheetPage.put(6, "toformal");
		sheetPage.put(7, "secoffer");
	}
	
	@RequestMapping(value = "/{num}")
    public String mapping(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String num,//sheet的编号
            Model model) {
		model.addAttribute("num", num);
		
		return "excel/sheet" + num;
    }
	
	@RequestMapping(value = "/serving/{sheetId}")
    public String serving(
    		HttpServletRequest request, HttpServletResponse response,
            @PathVariable Integer sheetId,//sheet的编号
            Model model) {
		//initBasicInfo(request, model, "花名册", "/roster/", sheetMap.get(sheetId), "/roster/serving/" + sheetId);
		return "roster/" + sheetPage.get(sheetId);
    }
	
	@RequestMapping(value = "/serving")
    public String serving(
            HttpServletRequest request, HttpServletResponse response,
            String type,
            Model model) {
		if(!StringUtils.isEmpty(type)){
			initBasicInfo(request, model, "花名册", "/roster/serving", "在职员工", "/roster/serving");
			model.addAttribute("isTab", "false");
		}
		return "roster/serving";
    }
	
	@RequestMapping(value = "/")
    public String roster(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		initBasicInfo(request, model, "花名册-Excel", "/roster/serving");
		return "roster/tab";
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param bookId
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/serving/list.json")
	@ResponseBody
    public EUIDataGridPage<Excel> list(
            HttpServletRequest request, 
            Excel excel,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Integer version = excelService.selectMaxVersion(excel.getSheetId());
		excel.setVersion(version);//每次查询最大的version的数据
		List<Excel> list = excelService.find(excel);
		EUIDataGridPage<Excel> rt = new EUIDataGridPage<Excel>();
		rt.setRows(list);
		long rows = excelService.count(excel);
		rt.setTotal(rows);
		return rt;
    }
	
	@RequestMapping(value = "/serving/del/{sheetId}")
	@ResponseBody
    public Map<String, Object> del(
            HttpServletRequest request,
            @PathVariable Integer sheetId,
            @RequestParam String ids,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int succ = 0;
		int fail = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(ids)){
			String[] arr = ids.split(",");
			for(String s : arr){
				if(excelService.delById(Integer.parseInt(s)) > 0){
					succ++;
				}else {
					fail++;
				}
			}
			map.put("succ", succ);
		}
		map.put("title", "提示信息");
		String msg = "成功删除：" + succ + "条";
		if(fail > 0) {
			msg = ",失败删除：" + fail  + "条";
		}
		map.put("msg", msg);

		//response.getWriter().write(JSON.toJSONString(map));
		return map;
	}
	
	@RequestMapping(value = "/serving/recovery/{sheetId}")
	@ResponseBody
    public int recovery(
            HttpServletRequest request,
            @PathVariable Integer sheetId,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//删除当前version最大的即可
		Integer version = excelService.selectMaxVersion(sheetId);
		if(version == 0) {
			return -1;
		}
		if(version.intValue() == 1){
			//只有一版
			return 0;
		}
		Excel excel = new Excel();
		excel.setSheetId(sheetId);
		excel.setVersion(version);
		int res = excelService.del(excel);
		return res;
	}
	
	@RequestMapping(value = "/serving/import.json")
	@ResponseBody
    public int importExcel(
            HttpServletRequest request,
            @RequestParam(required = true) Integer sheetId,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int res = 0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("excelFile");
			if(excelFile != null) {
				String type = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."),excelFile.getOriginalFilename().length());
				List<Excel> list = null;
				if(".xls".equals(type)){
					list = ExcelUtils.parse_97_2003(excelFile, sheetId);
				} else if(".xlsx".equals(type) ){
					list = ExcelUtils.parse_07_2010(excelFile, sheetId);
				}
				
				int saveSerObjResult = serObjService.create(list);
				List<Excel> data = list.subList(1, list.size());
				res = excelService.create(data, sheetId);
				//保存列
				Excel excelColume = list.subList(0, 1).get(0);
				int saveColumeResult = columeService.create(this.getColumes(excelColume, sheetId));
				this.saveColumeLog(excelColume);
				this.saveLog(data);
				logger.info("导入Sheet时,保存SerObj结果是：" + saveSerObjResult + ",保存Excel结果：" + res + ",保存colume的结果： " + saveColumeResult);
			}
		} catch (Exception e) {
			logger.error("导入Excel文件时异常", e);
			return 0;
		}
		
		return res;
    }
	
	private void saveColumeLog(Excel curCol) throws Exception{
		//如果column有变化需要记录
		List<Colume> curColList = this.getColumes(curCol, curCol.getSheetId());
		List<Colume> preColList = columeService.findBySheetId(curCol.getSheetId());
		if(preColList != null && curColList != null && curColList.size() > 0 && preColList.size() > 0){
			int size = preColList.size() > curColList.size() ? preColList.size() : curColList.size();
			StringBuffer info = new StringBuffer();
			for(int i=0; i<size; i++){
				if(i >= preColList.size()){
					info.append("新增列：" + curColList.get(i).getZnName() + "；");
				}else if(i >= curColList.size()){
					info.append("删除列：" + preColList.get(i).getZnName() + "；");
				} else if(!preColList.get(i).getZnName().equals(curColList.get(i).getZnName())){
					info.append("变化列：" + preColList.get(i).getZnName() + "变成了" + curColList.get(i).getZnName() + "；"); 
				}
			}
			SheetLog log = new SheetLog();
			if(info.length() > 4){
				log.setInfo(info.toString());
				//log.setTheme("操作:导入sheet" + sheetMap.get(curCol.getSheetId())+"，成功");
				log.setTheme(sheetMap.get(curCol.getSheetId()));
				log.setType(LogTypeEnum.UPDATE.getCode() + "");
				log.setSheetId(curCol.getSheetId());
				log.setCreateTime(new Date());
				log.setSheetVersion(excelService.selectMaxVersion(curCol.getSheetId()));
				sheetLogService.create(log);
			}else {
				//log.setTheme("操作:导入sheet" + sheetMap.get(curCol.getSheetId())+"，成功");
				//log.setInfo("列不变替换");
				//log.setType("insert_colume");
			}
		}
	}
	
	private void saveLog(List<Excel> curList){
		if(curList == null || curList.size() < 1) {
			return ;
		}
		int maxV = excelService.selectMaxVersion(curList.get(0).getSheetId());
		if(maxV < 2) {
			this.saveInsertSheetLog(curList.get(0).getSheetId(), maxV);
			return;
		}
		List<Excel> preList = excelService.find(curList.get(0).getSheetId(), maxV-1);
		if(preList == null || preList.size() == 0){
			this.saveInsertSheetLog(curList.get(0).getSheetId(), maxV);
			return;
		}
		boolean unChangeFlag = true;
		for(Excel curE : curList){
			Excel preE = this.getFormList(curE.getC02(), preList);
			String info = null;
			SheetLog log = new SheetLog();
			if(preE == null) {
				//新增行，直接记录新增的行的序号，删减同理
				info = "<a style='color:blue'>新增数据 ： </a>序号=" + curE.getC01() + "，工号="+ curE.getC02() + ",姓名=" + curE.getC03();
				log.setType(LogTypeEnum.ADD.getCode() + "");
			}else {
				String diff = null;
				try {
					diff = compare(curE, preE);
				} catch (Exception e) {
					logger.error("save log时，在比较Excel行的变化时，出错", e);
				}
				if(diff != null) {
					info = diff;
					log.setType(LogTypeEnum.UPDATE.getCode() + "");
				}
			}
			if(!StringUtils.isEmpty(info)){
				log.setSheetId(curE.getSheetId());
				log.setCreateTime(new Date());
				log.setSheetVersion(curE.getVersion()-1);
				log.setInfo(info);
				//log.setTheme("数据变化：导入sheet" + sheetMap.get(curE.getSheetId())+"操作");
				log.setTheme(sheetMap.get(curE.getSheetId()));
				sheetLogService.create(log);
				unChangeFlag = false;
			}
		}
		//如果是sheet变化，则以每行为单位记录，前后变化的日志【考虑到每行变化应该不大】
		for(Excel preE : preList) {
			Excel curE = this.getFormList(preE.getC02(), curList);
			if(curE == null) {
				SheetLog log = new SheetLog();
				//删除的情况： "<a style='color:green'>" + col.getZnName()+ "：</a>"
				String info = "<a style='color:orange'>删除数据 ：</a> 序号=" + preE.getC01() + "，工号="+ preE.getC02() + ",姓名=" + preE.getC03();
				log.setType(LogTypeEnum.DEL.getCode() + "");
				//log.setTheme("数据变化：导入sheet" + sheetMap.get(preE.getSheetId())+"操作");
				log.setTheme(sheetMap.get(preE.getSheetId()));
				log.setSheetId(preE.getSheetId());
				log.setCreateTime(new Date());
				log.setSheetVersion(preE.getVersion());
				log.setInfo(info);
				sheetLogService.create(log);
				unChangeFlag = false;
			}
		}
		if(unChangeFlag) {
			this.saveInsertSheetLog(curList.get(0).getSheetId(), maxV);
		}
	}
	
	private void saveInsertSheetLog(Integer sheetId, Integer version){
		SheetLog log = new SheetLog();
		String info = "<a style='color:bule'>插入sheet：</a>" + sheetMap.get(sheetId);
		log.setType(LogTypeEnum.ADD.getCode() + "");
		log.setTheme(sheetMap.get(sheetId));
		log.setSheetId(sheetId);
		log.setCreateTime(new Date());
		log.setSheetVersion(version);
		log.setInfo(info);
		sheetLogService.create(log);
	}
	
	public String compare(Excel curE, Excel preE) throws Exception{
		Method[] ms = curE.getClass().getMethods();
		StringBuffer sb = new StringBuffer();
		boolean isSame = true;
		List<Colume> list = columeService.findBySheetId(preE.getSheetId());
		Map<String, String> nameMap = new HashMap<String, String>();
		if(list != null){
			for(Colume col : list){
				nameMap.put(col.getEnName(), "<a style='color:green'>" + col.getZnName()+ "：</a>");
			}
		}
		for(Method m : ms){
			String metName = m.getName();
			if(metName.startsWith("getC")){
				metName = metName.replace("get", "");
				try {
					Integer.parseInt(metName.substring(1, metName.length()));
				} catch (Exception e) {
					continue;
				}
				Object o1 = m.invoke(curE); 
				Object o2 = m.invoke(preE);
				if(o1 == null && o2 != null) {
					if(sb.length() > 0) {
						sb.append("，");
					}
					sb.append(nameMap.get(metName)  + "由" + (String)o2 + "变成了空"); 
					isSame = false;
				}else if(o1 != null && o2 == null)  {
					if(sb.length() > 0) {
						sb.append("，");
					}
					sb.append(nameMap.get(metName) + "由空变成了" + (String)o1 + ""); 
					isSame = false;
				}else if(o1 != null && o2 != null){
					String s1 = o1.toString();
					String s2 = o2.toString();//此处不能强转，(String)o2会报错
					if(!s1.equals(s2)){
						isSame = false;
						if(sb.length() > 0) {
							sb.append("，");
						}
						sb.append(nameMap.get(metName) + "由" + s2+ "变成了" + s1); 
					}
				}
			}
		}
		if(isSame){
			return null;
		}
		return  sb.append("(<a style='color:red'>姓名:" + preE.getC03()+ "</a>)").toString();
	}
	
	private Excel getFormList(String c02, List<Excel> list){//c02是工号，可认为是唯一的
		if(list == null) return null;
		for(Excel e : list){
			if(c02.equals(e.getC02())){
				return e;
			}
		}
		return null;
	}
	
	private List<Colume> getColumes(Excel e, Integer sheetId) {
		List<Colume> list = new ArrayList<Colume>();
		Colume c1 = new Colume();
		c1.setSheetId(sheetId);
		c1.setZnName(e.getC01());
		list.add(c1);
				
		Colume c2 = new Colume();
		c2.setSheetId(sheetId);
		c2.setZnName(e.getC02());
		list.add(c2);

		Colume c3 = new Colume();
		c3.setSheetId(sheetId);
		c3.setZnName(e.getC03());
		list.add(c3);
				
		Colume c4 = new Colume();
		c4.setSheetId(sheetId);
		c4.setZnName(e.getC04());
		list.add(c4);

		Colume c5 = new Colume();
		c5.setSheetId(sheetId);
		c5.setZnName(e.getC05());
		list.add(c5);
				
		Colume c6 = new Colume();
		c6.setSheetId(sheetId);
		c6.setZnName(e.getC06());
		list.add(c6);

		Colume c7 = new Colume();
		c7.setSheetId(sheetId);
		c7.setZnName(e.getC07());
		list.add(c7);
				
		Colume c8 = new Colume();
		c8.setSheetId(sheetId);
		c8.setZnName(e.getC08());
		list.add(c8);

		Colume c9 = new Colume();
		c9.setSheetId(sheetId);
		c9.setZnName(e.getC09());
		list.add(c9);
				
		Colume c10 = new Colume();
		c10.setSheetId(sheetId);
		c10.setZnName(e.getC10());
		list.add(c10);

		Colume c11 = new Colume();
		c11.setSheetId(sheetId);
		c11.setZnName(e.getC11());
		list.add(c11);
				
		Colume c12 = new Colume();
		c12.setSheetId(sheetId);
		c12.setZnName(e.getC12());
		list.add(c12);

		Colume c13 = new Colume();
		c13.setSheetId(sheetId);
		c13.setZnName(e.getC13());
		list.add(c13);
				
		Colume c14 = new Colume();
		c14.setSheetId(sheetId);
		c14.setZnName(e.getC14());
		list.add(c14);

		Colume c15 = new Colume();
		c15.setSheetId(sheetId);
		c15.setZnName(e.getC15());
		list.add(c15);
				
		Colume c16 = new Colume();
		c16.setSheetId(sheetId);
		c16.setZnName(e.getC16());
		list.add(c16);

		Colume c17 = new Colume();
		c17.setSheetId(sheetId);
		c17.setZnName(e.getC17());
		list.add(c17);
				
		Colume c18 = new Colume();
		c18.setSheetId(sheetId);
		c18.setZnName(e.getC18());
		list.add(c18);

		Colume c19 = new Colume();
		c19.setSheetId(sheetId);
		c19.setZnName(e.getC19());
		list.add(c19);

		Colume c20 = new Colume();
		c20.setSheetId(sheetId);
		c20.setZnName(e.getC20());
		list.add(c20);
		
		Colume c21 = new Colume();
		c21.setSheetId(sheetId);
		c21.setZnName(e.getC21());
		list.add(c21);
				
		Colume c22 = new Colume();
		c22.setSheetId(sheetId);
		c22.setZnName(e.getC22());
		list.add(c22);

		Colume c23 = new Colume();
		c23.setSheetId(sheetId);
		c23.setZnName(e.getC23());
		list.add(c23);
				
		Colume c24 = new Colume();
		c24.setSheetId(sheetId);
		c24.setZnName(e.getC24());
		list.add(c24);

		Colume c25 = new Colume();
		c25.setSheetId(sheetId);
		c25.setZnName(e.getC25());
		list.add(c25);
				
		Colume c26 = new Colume();
		c26.setSheetId(sheetId);
		c26.setZnName(e.getC26());
		list.add(c26);

		Colume c27 = new Colume();
		c27.setSheetId(sheetId);
		c27.setZnName(e.getC27());
		list.add(c27);
				
		Colume c28 = new Colume();
		c28.setSheetId(sheetId);
		c28.setZnName(e.getC28());
		list.add(c28);

		Colume c29 = new Colume();
		c29.setSheetId(sheetId);
		c29.setZnName(e.getC29());
		list.add(c29);

		Colume c30 = new Colume();
		c30.setSheetId(sheetId);
		c30.setZnName(e.getC30());
		list.add(c30);

		Colume c31 = new Colume();
		c31.setSheetId(sheetId);
		c31.setZnName(e.getC31());
		list.add(c31);
				
		Colume c32 = new Colume();
		c32.setSheetId(sheetId);
		c32.setZnName(e.getC32());
		list.add(c32);

		Colume c33 = new Colume();
		c33.setSheetId(sheetId);
		c33.setZnName(e.getC33());
		list.add(c33);
				
		Colume c34 = new Colume();
		c34.setSheetId(sheetId);
		c34.setZnName(e.getC34());
		list.add(c34);

		Colume c35 = new Colume();
		c35.setSheetId(sheetId);
		c35.setZnName(e.getC35());
		list.add(c35);
				
		Colume c36 = new Colume();
		c36.setSheetId(sheetId);
		c36.setZnName(e.getC36());
		list.add(c36);

		Colume c37 = new Colume();
		c37.setSheetId(sheetId);
		c37.setZnName(e.getC37());
		list.add(c37);
				
		Colume c38 = new Colume();
		c38.setSheetId(sheetId);
		c38.setZnName(e.getC38());
		list.add(c38);

		Colume c39 = new Colume();
		c39.setSheetId(sheetId);
		c39.setZnName(e.getC39());
		list.add(c39);

		Colume c40 = new Colume();
		c40.setSheetId(sheetId);
		c40.setZnName(e.getC40());
		list.add(c40);

		Colume c41 = new Colume();
		c41.setSheetId(sheetId);
		c41.setZnName(e.getC41());
		list.add(c41);
				
		Colume c42 = new Colume();
		c42.setSheetId(sheetId);
		c42.setZnName(e.getC42());
		list.add(c42);

		Colume c43 = new Colume();
		c43.setSheetId(sheetId);
		c43.setZnName(e.getC43());
		list.add(c43);
				
		Colume c44 = new Colume();
		c44.setSheetId(sheetId);
		c44.setZnName(e.getC44());
		list.add(c44);

		Colume c45 = new Colume();
		c45.setSheetId(sheetId);
		c45.setZnName(e.getC45());
		list.add(c45);
				
		Colume c46 = new Colume();
		c46.setSheetId(sheetId);
		c46.setZnName(e.getC46());
		list.add(c46);

		Colume c47 = new Colume();
		c47.setSheetId(sheetId);
		c47.setZnName(e.getC47());
		list.add(c47);
				
		Colume c48 = new Colume();
		c48.setSheetId(sheetId);
		c48.setZnName(e.getC48());
		list.add(c48);
		
		return list;
	}
	
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/serving/export/{sheetId}/{type}")
	@ResponseBody
    public void exportExcel(
            HttpServletRequest request,
            @PathVariable Integer sheetId,
            @PathVariable String type,
            String ids,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String fileName = sheetMap.get(sheetId) + "." + type;
		response.setContentType("application/x-msdownload;");  
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
		java.io.BufferedOutputStream bos = null;  
		java.io.BufferedInputStream bis = null;
		byte[] buff = new byte[2048];  
		int bytesRead;  
		FileOutputStream fos = null;
		try {
			//TODO config path
			File excelFile = new File("D:/idy/"+ fileName);
			String[] idArr = ids.split(",");
			List<Excel> list = new ArrayList<Excel>();
			if("all".equals(ids)){
				Integer version = excelService.selectMaxVersion(sheetId);
				Excel excel = new Excel();
				excel.setVersion(version);//每次查询最大的version的数据
				excel.setSheetId(sheetId);
				list = excelService.find(excel);
			}else {
				for(String idstr : idArr) {
					Excel e = excelService.findById(Long.parseLong(idstr));
					list.add(e);
				}
			}
			Workbook workBook = null;
			if("xls".equals(type)){
				workBook = new HSSFWorkbook();
			}else {
				workBook = new XSSFWorkbook();
			}
			Sheet sheet = workBook.createSheet();
			CellStyle style = workBook.createCellStyle();// 创建样式对象
			//第一行
			List<Colume> first = columeService.findBySheetId(sheetId);
			if(first != null){
				Row rowData = sheet.createRow(0);
				for(int i= 0; i<first.size(); i++){
					Cell c = rowData.createCell(i);
					c.setCellValue(first.get(i).getZnName());
				}
			}
			//设置内容
			if(list != null) {
				for(int i=1; i<= list.size(); i++){
					Row rowData = sheet.createRow(i);// 创建一个行对象
					Cell c1 = rowData.createCell(0);// 创建单元格
					Cell c2 = rowData.createCell(1);// 创建单元格
					Cell c3 = rowData.createCell(2);// 创建单元格
					Cell c4 = rowData.createCell(3);// 创建单元格
					Cell c5 = rowData.createCell(4);// 创建单元格
					Cell c6 = rowData.createCell(5);// 创建单元格
					Cell c7 = rowData.createCell(6);// 创建单元格
					Cell c8 = rowData.createCell(7);// 创建单元格
					Cell c9 = rowData.createCell(8);// 创建单元格
					Cell c10 = rowData.createCell(9);// 创建单元格
					Cell c11 = rowData.createCell(10);// 创建单元格
					Cell c12 = rowData.createCell(11);// 创建单元格
					Cell c13 = rowData.createCell(12);// 创建单元格
					Cell c14 = rowData.createCell(13);// 创建单元格
					Cell c15 = rowData.createCell(14);// 创建单元格
					Cell c16 = rowData.createCell(15);// 创建单元格
					Cell c17 = rowData.createCell(16);// 创建单元格
					Cell c18 = rowData.createCell(17);// 创建单元格
					Cell c19 = rowData.createCell(18);// 创建单元格
					Cell c20 = rowData.createCell(19);// 创建单元格
					Cell c21 = rowData.createCell(20);// 创建单元格
					Cell c22 = rowData.createCell(21);// 创建单元格
					Cell c23 = rowData.createCell(22);// 创建单元格
					Cell c24 = rowData.createCell(23);// 创建单元格
					Cell c25 = rowData.createCell(24);// 创建单元格
					Cell c26 = rowData.createCell(25);// 创建单元格
					Cell c27 = rowData.createCell(26);// 创建单元格
					Cell c28 = rowData.createCell(27);// 创建单元格
					Cell c29 = rowData.createCell(28);// 创建单元格
					Cell c30 = rowData.createCell(29);// 创建单元格
					Cell c31 = rowData.createCell(30);// 创建单元格
					Cell c32 = rowData.createCell(31);// 创建单元格
					Cell c33 = rowData.createCell(32);// 创建单元格
					Cell c34 = rowData.createCell(33);// 创建单元格
					Cell c35 = rowData.createCell(34);// 创建单元格
					Cell c36 = rowData.createCell(35);// 创建单元格
					Cell c37 = rowData.createCell(36);// 创建单元格
					Cell c38 = rowData.createCell(37);// 创建单元格
					Cell c39 = rowData.createCell(38);// 创建单元格
					Cell c40 = rowData.createCell(39);// 创建单元格
					Cell c41 = rowData.createCell(40);// 创建单元格
					Cell c42 = rowData.createCell(41);// 创建单元格
					Cell c43 = rowData.createCell(42);// 创建单元格
					Cell c44 = rowData.createCell(43);// 创建单元格
					Cell c45 = rowData.createCell(44);// 创建单元格
					Cell c46 = rowData.createCell(45);// 创建单元格
					Cell c47 = rowData.createCell(46);// 创建单元格
					Cell c48 = rowData.createCell(47);// 创建单元格
					
					c1.setCellValue(list.get(i-1).getC01());
					c2.setCellValue(list.get(i-1).getC02());
					c3.setCellValue(list.get(i-1).getC03());
					c4.setCellValue(list.get(i-1).getC04());
					c5.setCellValue(list.get(i-1).getC05());
					c6.setCellValue(list.get(i-1).getC06());
					c7.setCellValue(list.get(i-1).getC07());
					c8.setCellValue(list.get(i-1).getC08());
					c9.setCellValue(list.get(i-1).getC09());
					c10.setCellValue(list.get(i-1).getC10());
					c11.setCellValue(list.get(i-1).getC11());
					c12.setCellValue(list.get(i-1).getC12());
					c13.setCellValue(list.get(i-1).getC13());
					c14.setCellValue(list.get(i-1).getC14());
					c15.setCellValue(list.get(i-1).getC15());
					c16.setCellValue(list.get(i-1).getC16());
					c17.setCellValue(list.get(i-1).getC17());
					c18.setCellValue(list.get(i-1).getC18());
					c19.setCellValue(list.get(i-1).getC19());
					c20.setCellValue(list.get(i-1).getC20());
					c21.setCellValue(list.get(i-1).getC21());
					c22.setCellValue(list.get(i-1).getC22());
					c23.setCellValue(list.get(i-1).getC23());
					c24.setCellValue(list.get(i-1).getC24());
					c25.setCellValue(list.get(i-1).getC25());
					c26.setCellValue(list.get(i-1).getC26());
					c27.setCellValue(list.get(i-1).getC27());
					c28.setCellValue(list.get(i-1).getC28());
					c29.setCellValue(list.get(i-1).getC29());
					c30.setCellValue(list.get(i-1).getC30());
					c31.setCellValue(list.get(i-1).getC31());
					c32.setCellValue(list.get(i-1).getC32());
					c33.setCellValue(list.get(i-1).getC33());
					c34.setCellValue(list.get(i-1).getC34());
					c35.setCellValue(list.get(i-1).getC35());
					c36.setCellValue(list.get(i-1).getC36());
					c37.setCellValue(list.get(i-1).getC37());
					c38.setCellValue(list.get(i-1).getC38());
					c39.setCellValue(list.get(i-1).getC39());
					c40.setCellValue(list.get(i-1).getC40());
					c41.setCellValue(list.get(i-1).getC41());
					c42.setCellValue(list.get(i-1).getC42());
					c43.setCellValue(list.get(i-1).getC43());
					c44.setCellValue(list.get(i-1).getC44());
					c45.setCellValue(list.get(i-1).getC45());
					c46.setCellValue(list.get(i-1).getC46());
					c47.setCellValue(list.get(i-1).getC47());
					c48.setCellValue(list.get(i-1).getC48());
					
					c1.setCellStyle(style);// 应用样式对象
					c2.setCellStyle(style);// 应用样式对象
					c3.setCellStyle(style);// 应用样式对象
				}
			}
			
			if(!excelFile.getParentFile().exists()){
				excelFile.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(excelFile); 
			workBook.write(fos);
			response.addHeader("Content-Length", "" + excelFile.length());
			bos = new BufferedOutputStream(response.getOutputStream());
			bis = new BufferedInputStream(new FileInputStream(excelFile)); 
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
				bos.write(buff, 0, bytesRead);  
			} 
		} catch (Exception e) {
			logger.error("导入Excel文件时异常", e);
		} finally {
			if (bis != null) bis.close();  
			if (bos != null) bos.close();
			if (fos != null) fos.close();
		}
		
		//return res;
    }
}

