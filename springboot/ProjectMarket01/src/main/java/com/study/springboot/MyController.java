package com.study.springboot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.study.springboot.dao.IGetUserInfoDao;
import com.study.springboot.dao.ITestBoardDao;
import com.study.springboot.dto.NoticeDto;
import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.ReplyDto;
import com.study.springboot.dto.TestBoardDto;
import com.study.springboot.dto.TokenDto;
import com.study.springboot.dto.UserDto;
import com.study.springboot.dto.UserInfoDto;
import com.study.springboot.service.IAndroidReservationService;
import com.study.springboot.service.IAndroidRestaurantService;
import com.study.springboot.service.ItAdminService;
import com.study.springboot.service.ItProjectService;


@Controller
public class MyController {
	
	@Autowired
	ItProjectService svc;
	
	@Autowired
	IAndroidRestaurantService rservice;
	@Autowired
	IAndroidReservationService iservice;
	//형두가 사용
	@Autowired
	ITestBoardDao testdao;
	@Autowired
	IGetUserInfoDao userdao;
	//지하 adimin
	@Autowired
	ItAdminService asvc;


	@RequestMapping("/")
	public String root(Model model) throws Exception {
		
		model.addAttribute("noticeTitle", asvc.noticeTitle());
		int nTotalCount = svc.noticeTitleCount();
		System.out.println("Count : " + nTotalCount);
		return "guest/main1";
	}
	
	@RequestMapping("/admin/content_view")
	public String content_view(HttpServletRequest request, Model model) throws Exception {
		String bId = request.getParameter("bId");
		model.addAttribute("noticeList", asvc.content_view(bId));
		return "guest/content_view";
	}


	@RequestMapping("/security/joinForm")
	public String joinView() {
		return "security/joinForm";
	}
	
	@RequestMapping("/security/join")
	public @ResponseBody String join(HttpServletRequest request, Model model,
			@ModelAttribute("dto") @Valid UserDto userDto, BindingResult result) {
		String page = "joinForm";

	
		String c_phone = request.getParameter("c_phone1") + "-" +  request.getParameter("c_phone2") + "-" + request.getParameter("c_phone3");
		userDto.setC_id(request.getParameter("c_id"));
		userDto.setC_pw(request.getParameter("c_pw"));
		userDto.setC_name(request.getParameter("c_name"));
		userDto.setC_phone(c_phone);
		userDto.setNickname(request.getParameter("nickname"));
		userDto.setC_email_id(request.getParameter("c_email_id"));
		userDto.setC_email_url(request.getParameter("c_email_url"));
		userDto.setC_email(userDto.getC_email_id() + "@" + userDto.getC_email_url());

		String password = userDto.getC_pw();

		password = new StandardPasswordEncoder().encode(password);

		Map<String, String> map = new HashMap<String, String>();
		map.put("item1", userDto.getC_name());
		map.put("item2", userDto.getC_id());
		map.put("item3", password);
//		map.put("item3", userDto.getC_pw());
		map.put("item4", userDto.getC_phone());
		map.put("item5", userDto.getC_email());
		map.put("item6", userDto.getNickname());

		int nResult = svc.join(map);
		System.out.println("join : " + nResult);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\", \"desc\":\"회원가입을 완료하였습니다.\"}";
		} else {
			json = "{\"code\":\"fail\", \"desc\":\"에러가 발생하여 회원가입에 실패했습니다.\"}";
		}

		return json;
	}
	
	@RequestMapping("/admin/deleteNotice")
	public @ResponseBody String deleteNotice(HttpServletRequest request) {

	
		String bId = request.getParameter("bId");

		int nResult = svc.deleteNotice(bId);
		System.out.println("delete : " + nResult);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\", \"desc\":\"게시물이 삭제되었습니다.\"}";
		} else {
			json = "{\"code\":\"fail\", \"desc\":\"알 수 없는 에러에 실패했습니다.\"}";
		}

		return json;
	}
	@RequestMapping("/admin/modifyNoticeForm")
	public String modifyNoticeForm(HttpServletRequest request, Model model) throws Exception {
		String bId = request.getParameter("bId");
		model.addAttribute("noticeList", asvc.content_view(bId));
		return "guest/modifyNoticeForm";
	}
	
	@RequestMapping("/security/modify")
	public @ResponseBody String modifyInfo(HttpServletRequest request, Model model) {
		

		String c_id = request.getParameter("c_id");
		String c_phone = request.getParameter("c_phone1") + "-" +  request.getParameter("c_phone2") + "-" + request.getParameter("c_phone3");
		String c_eMail = request.getParameter("c_email");
		String nickname = request.getParameter("nickname");
		

		Map<String, String> map = new HashMap<String, String>();
		map.put("item1", c_id);
		map.put("item2", c_phone);
		map.put("item3", c_eMail);
		map.put("item4", nickname);

		int nResult = svc.updateInfo(map);
		System.out.println("join : " + nResult);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\", \"desc\":\"회원정보 수정을 완료하였습니다.\"}";
		} else {
			json = "{\"code\":\"fail\", \"desc\":\"에러가 발생하여 수정에 실패했습니다.\"}";
		}

		return json;
	}
	
	@RequestMapping("/admin/modifyContent_view")
	public String modifyContent_view(HttpServletRequest request) throws Exception {
		
			 System.out.println("modifyContent_view");
				int size = 1024 * 1024 * 10;
				String file = "";
				String oriFile = "";
				
				JSONObject obj = new JSONObject();
				
				try{
					String path = ResourceUtils
								.getFile("classpath:static/upload").toPath().toString();
					
					ClassPathResource classPathResource = new ClassPathResource("static/upload");

					System.out.println("111111111111111111" + classPathResource.exists());
					MultipartRequest multi = new MultipartRequest(request,path, size, "UTF-8", new DefaultFileRenamePolicy());
					System.out.println(path);
					Enumeration files = multi.getFileNames();

						if(files.hasMoreElements()) {
							String str = (String)files.nextElement();
							System.out.println(str.length());
								file = multi.getFilesystemName(str);
								oriFile = multi.getOriginalFileName(str);
						}
				
					
					System.out.println(file);
					System.out.println(oriFile);
					
					String btitle = multi.getParameter("btitle");
					System.out.println(btitle);
					String bname = multi.getParameter("bname");
					System.out.println(bname);
					String bcontent = multi.getParameter("bcontent");
					String bId = multi.getParameter("bId");
					System.out.println(bcontent);
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("item1", btitle);
					map.put("item2",bname);
					map.put("item3", bcontent);
					map.put("item4", file);
					map.put("item5", bId);
							
					System.out.println(map.toString());
					
					int result = asvc.modifyContent_view(map);
					System.out.println(result);
			
				}catch(Exception e) {
					e.printStackTrace();
				}
		    
			return "redirect:/noticelist";
	}

	@RequestMapping("/security/checkId")
	public @ResponseBody String checkId(HttpServletRequest request) {
		System.out.println("checkid");
		String c_id = request.getParameter("c_id");
		System.out.println("c_id=" + c_id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("item1", c_id);
		System.out.println(map.toString());
		int nResult = svc.checkid(map);

		System.out.println("[" + nResult + "]");

		String json = "";

		if (nResult != 0) {// 결과 값이 있으면 아이디 존재
			json = "{\"code\":\"fail\", \"desc\":\"해당 아이디가 이미 존재합니다.\"}";
		} else { // 없으면 아이디 존재 X
			json = "{\"code\":\"success\", \"desc\":\"사용가능한 아이디입니다.\"}";
		}
		return json;
	}
	

	@RequestMapping("/myPage/changePwd")
	public @ResponseBody String changePwd(HttpServletRequest request) {
		
		String c_id = request.getParameter("c_id");
		String password = request.getParameter("nC_pw");
		String cPassword = request.getParameter("c_pw");

//		password = new StandardPasswordEncoder().encode(password);
		cPassword = new StandardPasswordEncoder().encode(cPassword);

		String nResult = svc.checkPwd(c_id);
		System.out.println("nResult=" + password);
		System.out.println("nResult=" + nResult);
		System.out.println(new StandardPasswordEncoder().matches(password, nResult));
		String json = "";
		if (!new StandardPasswordEncoder().matches(password, nResult)) {// 결과 값이 있으면 아이디 존재
			json = "{\"code\":\"fail\", \"desc\":\"현재 비밀번호 맞지않습니다.\"}";
			
		} else { // 없으면 아이디 존재 X
			svc.insertPwd(cPassword, c_id);
			json = "{\"code\":\"success\", \"desc\":\"비밀번호가 변경되었습니다.\"}";
		}

	
		return json;
	}
	


	@RequestMapping("/security/checkNick")
	public @ResponseBody String checkNick(HttpServletRequest request) {
		String nickname = request.getParameter("nickname");
		System.out.println("nickname=" + nickname);
		Map<String, String> map = new HashMap<String, String>();
		map.put("item6", nickname);

		System.out.println(map);

		int nResult = svc.checkNick(map);

		System.out.println("[" + nResult + "]");

		String json = "";

		if (nResult != 0) {// 결과 값이 있으면 아이디 존재
			json = "{\"code\":\"fail\", \"desc\":\"해당 닉네임이 이미 존재합니다.\"}";
		} else { // 없으면 아이디 존재 X
			json = "{\"code\":\"success\", \"desc\":\"사용가능한 닉네임입니다.\"}";
		}
		return json;
	}

	@RequestMapping("/loginForm")
	public String loginFrom() {
		return "security/loginForm";
	}
	@RequestMapping("/changePwd")
	public String changePwd() {
		return "guest/changePwd";
	}
	
    //예약리스트 가져오기
    @RequestMapping("/notice")
	public String adminBookedList(HttpServletRequest request, Model model) {
	
    	System.out.println("예약리스트 가져오기 ");
		int nPage = 1;
		try
		{
			String sPage = request.getParameter("page");
			 
			System.out.println(sPage);
			nPage = Integer.parseInt(sPage);
			
			
		}catch(Exception e)
		{
	
		}
		System.out.println("nPage : " + nPage);
		
		model.addAttribute("page", svc.noticeCount(nPage));
	
		nPage = svc.noticeCount(nPage).get(0).getCurPage();
		
		model.addAttribute("noticeList", svc.noticeList(nPage));
	
		return "guest/bookedlist";
	}
    
  //예약리스트(검색결과) 가져오기
    @RequestMapping("/searchNotice")
	public String adminBookedListSearch(HttpServletRequest request, Model model) {
	
    	System.out.println("예약리스트(검색결과)  가져오기 ");
		int nPage = 1;
		try
		{
			String sPage = request.getParameter("page");
			 
			System.out.println(sPage);
			nPage = Integer.parseInt(sPage);
			
			
		}catch(Exception e)
		{
	
		}
		String category = request.getParameter("category");
		String content = request.getParameter("content");
		System.out.println("search : " + category + content);
	
		model.addAttribute("page", svc.searchNoticeCount(nPage,category,content));
	
		nPage = svc.noticeCount(nPage).get(0).getCurPage();
		System.out.println("nPage : " + nPage);
		model.addAttribute("noticeList", svc.searchNoticeList(nPage,category,content));
	
		return "guest/searchBookedlist";
	}
    
    @RequestMapping("/member/noticeForm")
	public String noticeForm(HttpServletRequest request, Model model, String uId) {
    	
    	uId = userInfo(null);
//    	System.out.println(uId.toString());
    	
    	UserDto uDto = svc.userSelect(uId);
    	System.out.println("nickname = " + uDto.getNickname().toString());
    	
    	model.addAttribute("nickname", uDto.getNickname());
    	
		return "member/noticeForm";
	}
    
    @RequestMapping("/checkReservation")
	public @ResponseBody String checkReservation(HttpServletRequest request, Model model) {
    	String json="";
    	
    	String r_rsvnumber = request.getParameter("r_rsvnumber");
    	int nResult = (testdao.checkReservation(r_rsvnumber));
    	System.out.println("r_rsvnumber" + r_rsvnumber);
    	System.out.println("nResult" + nResult);
    	if (nResult == 1) {
			json = "{\"code\":\"success\", \"desc\":\"예약확인 되었습니다.\"}";
		} else {
			json = "{\"code\":\"fail\", \"desc\":\"예약확인에 실패하였습니다.\"}";
		}
    
    	
		return json;
	}
    

    
    public String userInfo(@AuthenticationPrincipal User user) {
    	String uId = (SecurityContextHolder.getContext().getAuthentication().getName().toString());
//    	System.out.println(uId.toString());
		return uId;
    }
    
    @RequestMapping("/fileUpload")
    public String imgUpload(HttpServletRequest request, HttpServletResponse response) {
    	
    	String fileInfo = "";
    	String oriFileName = "";
    	String fileName = request.getHeader("file-name");
    	JSONObject obj = new JSONObject();
    	
    	try {
    		String path = ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
//    		System.out.println(path);
    		
    		oriFileName = fileName;
    		String reFileName = path + "/" + oriFileName;
    		System.out.println("1."+reFileName);
    		
    		InputStream input = request.getInputStream();
    		OutputStream output = new FileOutputStream(reFileName);
    		int numRead;
    		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
    		while((numRead = input.read(b,0,b.length)) != -1) {
    			output.write(b,0,numRead);
    		}
    		if(input != null) {
    			input.close();
    		}
    		System.out.println("2."+path);
    		output.flush();
    		output.close();
    		
    		fileInfo += "&bNewLine=true";
    		fileInfo += "&sFileName="+fileName;
    		fileInfo += "&sFileURL="+"/upload/"+oriFileName;
    		System.out.println("fileInfo = "+fileInfo);
    		PrintWriter out = response.getWriter();
    		out.print(fileInfo);
    		out.flush();
    		out.close(); 
    		
    	} catch(Exception e) {
    	}
		return "";
    }
               
	@RequestMapping("/main")
	public String main2() {
		System.out.println("/main");
		return "security/main2";
	}
	@RequestMapping("/security/main")
	public String secmain2() {
		System.out.println("security/main");
		return "security/main2";
	}
	
	@RequestMapping("/myPageMain")
	public String myPageInfo(HttpServletRequest request,Model model) {
		System.out.println("CONTROLLER: myPageMain");
		String c_id = request.getParameter("c_id");
		System.out.println(c_id);
		System.out.println("userName : "+asvc.myPageInfo("c_id"));
		model.addAttribute("myPageInfo", asvc.myPageInfo(c_id));
		return "/guest/myPageMain";
			
	}
	@RequestMapping("/changeMyInfo")
	public String changeMyInfo(HttpServletRequest request,Model model) {
		System.out.println("CONTROLLER: myPageMain");
		String c_id = request.getParameter("c_id");
		System.out.println(c_id);
		System.out.println("userName : "+asvc.myPageInfo("c_id"));
		List<UserInfoDto> info = asvc.myPageInfo(c_id);
		String[] c_phone = info.get(0).getC_phone().split("-");
		model.addAttribute("myPageInfo", asvc.myPageInfo(c_id));
		model.addAttribute("c_phone", c_phone);
		String test = c_phone[0];
	
		return "/guest/changeMyInfo";
			
	}

	@RequestMapping("admin/search") //관리자 검색(일반 한정)
	public String search_a(HttpServletRequest request, Model model) throws Exception {
		String sPlace = request.getParameter("searchplace");
		String sp = "%" + sPlace + "%";
		System.out.println(sp);
		model.addAttribute("list", svc.searchReject(sp));
		model.addAttribute("list2", svc.searchRequest(sp));
		return "search";
	}	
	
//	##=======================admin 지하========================================
	
	@RequestMapping("/noticelist")
	public String getNoticeList(HttpServletRequest request, Model model){
		System.out.println("Controller: getNoticeList method");

		int nPage = 1;
		try
		{
			String sPage = request.getParameter("page");
			 
			System.out.println(sPage);
			nPage = Integer.parseInt(sPage);
			
			
		}catch(Exception e)
		{
	
		}
		System.out.println("nPage : " + nPage);
		
		model.addAttribute("page", svc.noticeCount(nPage));
		nPage = svc.noticeCount(nPage).get(0).getCurPage();
		System.out.println("nPage : " + nPage);
		model.addAttribute("noticeList", asvc.noticeList(nPage));
		
	
		System.out.println("Count : " + svc.noticeCount(nPage));
		
		return "guest/snoticelist";
	}
	
	    
    @RequestMapping("/donoticewrite")
	public String adminNoticeForm(HttpServletRequest request, Model model) {
    	
    	String user=request.getParameter("user");
    	System.out.println("request user: "+user);
    	
    	model.addAttribute("nickname", asvc.getUserNickname(user));
    	
		return "guest/noticeForm";
	}
    
    
    
	  @RequestMapping("/admin/noticeWrite")
		public String noticeWrite(HttpServletRequest request, Model model)
	    {
		 System.out.println("noticeWrite");
			int size = 1024 * 1024 * 10;
			String file = "";
			String oriFile = "";
			
			JSONObject obj = new JSONObject();
			
			try{
				String path = ResourceUtils
							.getFile("classpath:static/upload").toPath().toString();
				
				ClassPathResource classPathResource = new ClassPathResource("static/upload");

				System.out.println("111111111111111111" + classPathResource.exists());
				MultipartRequest multi = new MultipartRequest(request,path, size, "UTF-8", new DefaultFileRenamePolicy());
				System.out.println(path);
				Enumeration files = multi.getFileNames();

					if(files.hasMoreElements()) {
						String str = (String)files.nextElement();
						System.out.println(str.length());
							file = multi.getFilesystemName(str);
							oriFile = multi.getOriginalFileName(str);
					}
			
				
				System.out.println(file);
				System.out.println(oriFile);
				
				String btitle = multi.getParameter("btitle");
				System.out.println(btitle);
				String bname = multi.getParameter("bname");
				System.out.println(bname);
				String bcontent = multi.getParameter("bcontent");
				System.out.println(bcontent);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("item1", btitle);
				map.put("item2",bname);
				map.put("item3", bcontent);
				map.put("item4", file);
						
				System.out.println(map.toString());
				
				asvc.noticeWrite(map);
		
			}catch(Exception e) {
				e.printStackTrace();
			}
	    	

			return "redirect:/noticelist";
		
	    }
	  //리뷰리스트 가져오기
	  @RequestMapping("/android/getreviewlist")
	  public @ResponseBody String getReviewList(HttpServletRequest request) {
		  System.out.println("Controller: getReviewList");
		  return rservice.getReviewList(request.getParameter("m_number"), request.getParameter("r_name"));
	  }
	  
	  //푸쉬알림
		@RequestMapping("/SendMessage")
		public String sendMessage() throws Exception {
			System.out.println("sendMessage ");
			return "admin/SendMessage";
		}
		//예약 체크
		@RequestMapping("/android/checkReservation")
		public @ResponseBody String checkReservation(HttpServletRequest request) throws Exception {
			System.out.println("checkReservation ");
			String r_rsvnumber = request.getParameter("r_rsvnumber");
			String check=rservice.checkReservation(r_rsvnumber);
			
			System.out.println("controller checkReservation: "+check);
			return check;
		}
		//노쇼 체크 
			@RequestMapping("/android/noCheckReservation")
			public @ResponseBody String noCheckReservation(HttpServletRequest request) throws Exception {
				System.out.println("noCheckReservation ");
				String r_rsvnumber = request.getParameter("r_rsvnumber");
				String check=rservice.noCheckReservation(r_rsvnumber);
				
				System.out.println("controller noCheckReservation: "+check);
				return check;

		}
		@RequestMapping("/sendMessageOk")
		public String sendMessageOk(HttpServletRequest request, Model model) throws Exception {
			System.out.println("SendMessageOk ");
			int i=0;

			String notiTitle = request.getParameter("notiTitle");
			String notiBody = request.getParameter("notiBody");
			String msg = request.getParameter("message");
			String ApiKey = "AAAAixAXZos:APA91bE_AsUSyoK1BX8QAvudcQ_FKFuDg1j50pKIyj6LGF33ogCYuD_EA_Iu5M6x0HJMlAVsBMPARvFfZj5hfvJX5xEa4DJnHDxp9_6RAqIFWYltCintmhEpBfLK07QFD0JoAo7eT-1z";
			String fcmURL = "https://fcm.googleapis.com/fcm/send";
			
			try{
				//토큰 넣는곳
				JSONArray deviceId = new JSONArray();
				List<TokenDto> list = asvc.tokenList();
				while(i < list.size()) {
					deviceId.add(list.get(i).getToken());
					System.out.println(list.get(i).getToken());
					i++;
				}
				
				
				
				URL url = new URL(fcmURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization", "key=" + ApiKey);
				conn.setRequestProperty("Content-Type", "application/json");
				
				JSONObject json = new JSONObject();
				JSONObject noti = new JSONObject();
				noti.put("title",notiTitle);
				noti.put("body",notiBody);
				
				JSONObject data = new JSONObject();
				data.put("message", msg);
				
				// json.put("to", deviceId);   //한명에게 보낼 때 
				json.put("registration_ids", deviceId);  //여러명 보낼 때 
				json.put("notification", noti);
				json.put("data", data);
							
				try{
					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					wr.write(json.toString());
					wr.flush();
					
					BufferedReader br = new BufferedReader((new InputStreamReader(conn.getInputStream())));
					String output;
					System.out.println("Output from Server ....\n");
					while((output = br.readLine()) != null){
						System.out.println(output);
					}
				}catch(Exception e){
					e.printStackTrace()	;
				}
//				out.print(notiTitle + "<br>");
//				out.print(notiBody + "<br>");
//				out.print("Firebase Cloud Message 발송");
				model.addAttribute("notiTitle",notiTitle);		
				model.addAttribute("notiBody",notiBody);
				model.addAttribute("msg",msg);
			}catch(Exception e){
				e.printStackTrace()	;
			}
			
			return "admin/SendMessageOk";
		}
	//사이렌 예약리스트 가져오기
	@RequestMapping("/sirenlist")
	public String adminSirenList(Model model) {
		model.addAttribute("sirenList", asvc.sirenList());
			
//		int nTotalCount = svc.noticeCount();
//		System.out.println("Count : " + nTotalCount);
		return "admin/sirenlist";
	}
	//
	@RequestMapping("/selectchat")
	public String adminSelectChat(Model model) {
		System.out.println("CONTROLLER: admin select ChatList");
		return "admin/selectchat";
	}
	//
	@RequestMapping("admin/classchatlist")
	public String adminChatList(HttpServletRequest request,Model model) {
		System.out.println("CONTROLLER: adminChatList");
		String type=request.getParameter("type");
		
		model.addAttribute("type", type);
		return "admin/classchatlist";
	}
	
	//
	@RequestMapping("admin/managerclasschat")
	public String adminChatPage(HttpServletRequest request,Model model) {
		System.out.println("CONTROLLER: adminChatPage");
		
//		System.out.println("classid:"+request.getParameter("classid"));
//		model.addAttribute("classid", request.getParameter("classid"));
		System.out.println("userName : "+request.getParameter("userName"));
		model.addAttribute("userName", request.getParameter("userName"));
		model.addAttribute("type", request.getParameter("type"));
		return "admin/managerclasschat";
			
	}
	
	
	
//	==================안드로이드====================
	//인기맛집 리스트 목록
		@RequestMapping("/android/likelist")
		public @ResponseBody String likelistBoard(Model model) {
			System.out.println("Controller:likelist data");
			String likelist=rservice.getlikeList();
			return likelist;
		}
		//최근등록 맛집 리스트
		@RequestMapping("/android/recentlist")
		public @ResponseBody String RecentlistBoard(Model model) {
			System.out.println("Controller:RecentListBoard");
			String recentlist=rservice.getrecentList();
			
			System.out.println("controller recentlist: "+recentlist);
			return recentlist;
		}

		
		//검색어 입력시 검색매장 출력
		@RequestMapping("/android/search")
		public @ResponseBody String searchlistBoard(HttpServletRequest request) {
			System.out.println("Controller:searchlist data");
			
			String searchword=request.getParameter("search");
			System.out.println("검색어: "+searchword);
			
			String searchlist=rservice.getsearchList(searchword);
			return searchlist;
		}
		
		//검색어 입력시 검색매장 메뉴 출력
			@RequestMapping("/android/getmenu")
			public @ResponseBody String getMenuList(HttpServletRequest request) {
				System.out.println("Controller:menulist data");
				
				String mnumber=request.getParameter("mnumber");
				String storename=request.getParameter("rname");
				System.out.println(mnumber+":"+storename);
				
				String menulist=rservice.getMenuList(mnumber, storename);
				System.out.println("controller menulist:"+menulist);
				return menulist;
			}

			//검색어 입력시 검색매장 정보 출력
			@RequestMapping("/android/getresinfo")
			public @ResponseBody String getResInfoList(HttpServletRequest request) {
				System.out.println("Controller:resinfolist data");
				
				String mnumber=request.getParameter("mnumber");
				String storename=request.getParameter("rname");
				System.out.println(mnumber+":"+storename);
				
				String infolist=rservice.getRestaurantInfo(mnumber, storename);
				System.out.println("controller infolist:"+infolist);
				return infolist;
			}

			//예약시 로그인 아이디로 회원정보 가져오기
			@RequestMapping("/android/getrsvmeminfo")
			public @ResponseBody String getRsvMemInfo(HttpServletRequest request) {
				System.out.println("Controller:getrsvmeminfo data");
				
				String c_id=request.getParameter("c_id");

				System.out.println("c_id: "+c_id);
				
				String meminfo=iservice.getRsvMemInfo(c_id);
				return meminfo;
			}
			//예약정보 저장
			@RequestMapping("/android/insertreserveinfo")
			public @ResponseBody int insertReserveInfo(HttpServletRequest request) {
				System.out.println("Controller: insert reserve info");
				
				Map<String, Object> rsv=new HashMap<>();
				rsv.put("c_id",request.getParameter("c_id"));
				rsv.put("r_rsvnumber", request.getParameter("r_rsvnumber"));
				rsv.put("m_number", request.getParameter("m_number"));
				rsv.put("r_name", request.getParameter("r_name"));
				rsv.put("nickname", request.getParameter("nickname"));
				rsv.put("c_phone", request.getParameter("c_phone"));
				rsv.put("b_party", request.getParameter("b_party"));
				rsv.put("condition_check", request.getParameter("condition_check"));
				rsv.put("res_payment", request.getParameter("res_payment"));
				rsv.put("tdate", request.getParameter("tdate"));
				rsv.put("time", request.getParameter("time"));
				if(!(request.getParameter("request")).equals(null)) {
					System.out.println("예약요청사항 not null");
					rsv.put("request", request.getParameter("request"));
				}
				System.out.println("controller put map : "+rsv);
				
				int result=iservice.insertReservationInfo(rsv);
				return result;
			}
			//예약완료시 예약고유번호로 예약정보 가져오기
			@RequestMapping("/android/getrsvinfo")
			public @ResponseBody String getRsvInfo(HttpServletRequest request) {
				System.out.println("Controller:getrsvinfo data");
				
				String r_rsvnumber=request.getParameter("r_rsvnum");
				System.out.println("r_rsvnumber: "+r_rsvnumber);
						
				String rsvinfo=iservice.getRsvInfo(r_rsvnumber);
				return rsvinfo;
			}
			//좋아요 확인		
			@RequestMapping("/android/checklike")
			public @ResponseBody int checkLike(HttpServletRequest request) {
				System.out.println("Controller: checkLike");
				String c_id=request.getParameter("c_id");
				String r_name=request.getParameter("r_name");
				String m_number=request.getParameter("m_number");
				
				int nResult=rservice.checkLike(m_number, r_name, c_id);
				System.out.println("controller checklike result: "+nResult);
				return nResult;
			}
			//좋아요
			@RequestMapping("/android/dolike")
			public @ResponseBody int doLike(HttpServletRequest request) {
				System.out.println("Controller: doLike");
				String c_id=request.getParameter("c_id");
				String r_name=request.getParameter("r_name");
				String m_number=request.getParameter("m_number");
				
				int nResult=rservice.doLike(m_number, r_name, c_id);
				return nResult;
			}
			//좋아요 취소
			@RequestMapping("/android/undolike")
			public @ResponseBody int undoLike(HttpServletRequest request) {
				System.out.println("Controller: undoLike");
				String c_id=request.getParameter("c_id");
				String r_name=request.getParameter("r_name");
				String m_number=request.getParameter("m_number");
				
				int nResult=rservice.undoLike(m_number, r_name, c_id);
				return nResult;
			}
			//좋아요 리스트 
			@RequestMapping("/android/getlikelist")
			public @ResponseBody String getLikeList(HttpServletRequest request) {
				System.out.println("COntroller : getlike list");
				String c_id=request.getParameter("id");
				
				String result=rservice.getLikeList(c_id);
				return result;
			}
//			============================호범
			// 로그인
			@RequestMapping("/android/applogin")
			public @ResponseBody int appLogin(HttpServletRequest request) {
				System.out.println("Controller:Login");
				String c_id = request.getParameter("c_id");
				String c_pw = request.getParameter("c_pw");

				Map<String, String> map = new HashMap<String, String>();
				map.put("item1", c_id);
				map.put("item2", c_pw);
				System.out.println(map.toString());

				int nResult = rservice.getLoginResult(map);

				return nResult;
			}
			@RequestMapping("/android/checkId")
			public @ResponseBody String andcheckId(HttpServletRequest request) {
				System.out.println("아이디 중복 확인");
				String c_id = request.getParameter("c_id");
			

				Map<String, String> map = new HashMap<String, String>();
				map.put("c_id", c_id);
			
				System.out.println(map.toString());

				int nResult = rservice.andCheckId(map);
				
				System.out.println(nResult);
				
				 JSONObject fjb = new JSONObject(); 
				 JSONArray jsonArray1 = new JSONArray();
				
					JSONObject jb = new JSONObject();
				
					jb.put("result",nResult);
			
					fjb.put("restaurnatList",jb);
					System.out.println(fjb.toString());
					

				return String.valueOf(nResult);
			}
			
			@RequestMapping("/android/checkNickName")
			public @ResponseBody String checkNickName(HttpServletRequest request) {
				System.out.println("아이디 중복 확인");
				String nickName = request.getParameter("nickName");
			

				Map<String, String> map = new HashMap<String, String>();
				map.put("nickName", nickName);
			
				System.out.println(map.toString());

				int nResult = rservice.andCheckNickName(map);
				
				System.out.println(nResult);
				
				JSONObject fjb = new JSONObject(); 
				JSONArray jsonArray1 = new JSONArray();
				JSONObject jb = new JSONObject();
				jb.put("result",nResult);
				fjb.put("restaurnatList",jb);
				System.out.println(fjb.toString());
				
				return String.valueOf(nResult);
			}

			// 회원가입
			@RequestMapping("/android/join")
			public @ResponseBody int joinMembership(HttpServletRequest request) {
				System.out.println("join");

				System.out.println(request.getParameter("userid"));

				String c_name = request.getParameter("c_name");
				String c_id = request.getParameter("c_id");
				String c_pw = request.getParameter("c_pw");
				String c_phone = request.getParameter("c_phone");
				String c_eMail = request.getParameter("c_email");
				String nickname = request.getParameter("nickname");
				String token = request.getParameter("token");

				Map<String, String> map = new HashMap<String, String>();
				map.put("item1", c_name);
				map.put("item2", c_id);
				map.put("item3", c_pw);
				map.put("item4", c_phone);
				map.put("item5", c_eMail);
				map.put("item6", nickname);
				map.put("item7", token);
				System.out.println(map);

				int nResult = rservice.getJoinResult(map);

				System.out.println("join=" + nResult);
				return nResult;
			}

			// 회원의 예약 내역
			@RequestMapping("/android/c_res_list")
			public @ResponseBody String customerResList(HttpServletRequest request, Model model) {
				System.out.println("Controller:CustomerResList");
				String c_id = request.getParameter("c_id");
				String customerResList = rservice.getCustomerResList(c_id);
				return customerResList;
			}
			//업주 가게 예약 리스
			@RequestMapping("/android/reservationList")
			public @ResponseBody String reservationList(HttpServletRequest request, Model model) {
				System.out.println("Controller:reservationList111111111111");
				String c_id = request.getParameter("c_id");
				String customerResList = rservice.getReservationList(c_id);
				return customerResList;
			}

			// Frg5 화면 데이터 불러오기
			@RequestMapping("/android/myPage")
			public @ResponseBody String myPage(HttpServletRequest request) {
				System.out.println("Controller:MyPage");
				String c_id = request.getParameter("c_id");
				String myProfile = rservice.getMyProfile(c_id);
				return myProfile;
			}

			// 회원정보 수정에 필요한 데이터 불러오기
			@RequestMapping("/android/my_profile_data")
			public @ResponseBody String myProfileMod(HttpServletRequest request) {
				System.out.println("Controller:MyPage");
				String c_id = request.getParameter("c_id");
				String modifyBaseData = rservice.getModifyData(c_id);
				return modifyBaseData;
			}

			// 회원정보 수정 전 본인확인
			@RequestMapping("/android/checkPwd")
			public @ResponseBody int checkPwd(HttpServletRequest request) {
				System.out.println("Controller:Check Password");
				String c_id = request.getParameter("c_id");
				String c_pw = request.getParameter("c_pw");

				Map<String, String> map = new HashMap<String, String>();
				map.put("item1", c_id);
				map.put("item2", c_pw);
				System.out.println(map.toString());

				int nResult = rservice.getLoginResult(map);

				return nResult;
			}

			// 회원정보 수정(닉네임 제외)
			@RequestMapping("/android/modify_profile")
			public @ResponseBody int setModifyMyProfile(HttpServletRequest request) {
				System.out.println("Controller:Set Modify MyProfile");

				System.out.println(request.getParameter("userid"));

				String c_name = request.getParameter("c_name");
				String c_id = request.getParameter("c_id");
				String c_pw = request.getParameter("c_pw");
				String c_phone = request.getParameter("c_phone");
				String c_eMail = request.getParameter("c_email");

				Map<String, String> map = new HashMap<String, String>();
				map.put("item1", c_pw);
				map.put("item2", c_phone);
				map.put("item3", c_eMail);
				map.put("item4", c_id);
				System.out.println(map);

				int nResult = rservice.setUpdateMyProfile(map);

				System.out.println("modify result=" + nResult);
				return nResult;
			}
			
			// 내가 쓴 리뷰
			@RequestMapping("/android/my_review")
			public @ResponseBody String myReview(HttpServletRequest request, Model model) {
				System.out.println("Controller:MyReview");

				String c_id = request.getParameter("c_id");
				System.out.println("getParameter(c_id) = " + c_id);

				String myReviewList = rservice.getMyReview(c_id);

				return myReviewList;
			}
			
			// 점주의 가게 데이터
			@RequestMapping("/android/getresinfo_mg")
			public @ResponseBody String getResInfoListMg(HttpServletRequest request) {
				System.out.println("Controller:resinfolist data");

				String m_id = request.getParameter("m_id");
				System.out.println(m_id);

				String infolist = rservice.getRestaurantInfoMg(m_id);
				System.out.println("controller infolist:" + infolist);
				return infolist;
			}
			
			// 점주의 가게 정보수정
			@RequestMapping("/android/storedata_insert")
			public @ResponseBody int setStoreUpdate(HttpServletRequest request) {
				System.out.println("Controller:resinfo data Update");
				
				String r_number = request.getParameter("r_number");
				String r_menu = request.getParameter("r_menu");
				String m_id = request.getParameter("m_id");
				
				Map<String, String> map = new HashMap<>();
				map.put("item1", r_number);
				map.put("item2", r_menu);
				map.put("item3", m_id);
				System.out.println(map.toString());
				
				int nResult = rservice.setStoreUpdate(map);
				
				return nResult;
			}

			// 닉네임 변경
			@RequestMapping("/android/nick_update")
			public @ResponseBody int setUpdateNickname(HttpServletRequest request) {
				System.out.println("Controller:Nickname Update");
				
				String nickname = request.getParameter("nickname");
				String c_id = request.getParameter("c_id");
				
				int nResult = rservice.setUpdateNickname(nickname, c_id);
				
				return nResult;
			}

//			#===========================형두==============
			@RequestMapping("/android/ForMarker")
			public @ResponseBody String restaurant_map(HttpServletRequest request,Model model) {
				System.out.println("restaurant_map");
				String latitude = request.getParameter("latitude");
				String longitude = request.getParameter("longitude");
				int i;
//				String forSqlAddress = "%"+ address+"%";
//				System.out.println(forSqlAddress);
				System.out.println(latitude);
				System.out.println(longitude);
				ArrayList<TestBoardDto> list = testdao.testSelect(latitude,longitude);

				 JSONObject fjb = new JSONObject(); 
				 JSONArray jsonArray1 = new JSONArray();
				for(i=0; i<list.size(); i++) {
					JSONObject jb = new JSONObject();
				
					jb.put("r_name", list.get(i).getR_name());
					jb.put("r_number", list.get(i).getR_number());
					jb.put("r_adress1", list.get(i).getR_adress1() +  list.get(i).getR_adress2());
					System.out.println(list.get(i).getR_adress1() +  list.get(i).getR_adress2());
					jb.put("r_menu", list.get(i).getR_menu());
					jb.put("m_number", list.get(i).getM_number());
				
					
					
					jsonArray1.add(jb);
					fjb.put("restaurnatList",jsonArray1);
					System.out.println(fjb.toString());
					
					 
				}
				
			
				
				return fjb.toString();
			}
			
			@RequestMapping("/android/sirenOrder")
			public @ResponseBody String sirenOrder(HttpServletRequest request,Model model) {
				System.out.println("sirenOrder");
				String sirenOrder = request.getParameter("json");
				System.out.println(sirenOrder);
				int sirenOrderResult = 3;
				try {
					//JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
					JSONParser jsonParse = new JSONParser();
					JSONObject jsonobj = (JSONObject)jsonParse.parse(sirenOrder);
					String r_name = (String)jsonobj.get("r_name");
					System.out.println(r_name);
					String m_number = (String)jsonobj.get("m_number");
					System.out.println(m_number);
					String c_id = (String)jsonobj.get("c_id");
					System.out.println(c_id);
					String c_name = (String)jsonobj.get("c_name");
					System.out.println(c_name);
					String c_phone = (String)jsonobj.get("c_phone");
					System.out.println(c_phone);
					String res_payment = (String)jsonobj.get("res_payment");
					System.out.println(res_payment);
					String r_menu = (String)jsonobj.get("r_menu");
					System.out.println(r_menu);
					String userRequest = (String)jsonobj.get("request");
					System.out.println(userRequest);
					String r_rsvnum = (String)jsonobj.get("r_rsvnum");
					System.out.println(r_rsvnum);
					sirenOrderResult = testdao.sirenOrder(m_number,r_name,c_id,c_name,c_phone,res_payment,r_menu,userRequest,r_rsvnum);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			
				
				return String.valueOf(sirenOrderResult);
			}
			
			@RequestMapping("/android/getUserInfo")
			public @ResponseBody String getUserInfo(HttpServletRequest request,Model model) {
				System.out.println("getUserInfo");
				String c_id = request.getParameter("c_id");
				System.out.println(c_id);
				JSONObject fjb = new JSONObject(); 
				JSONArray jsonArray1 = new JSONArray();
				JSONObject jb = new JSONObject();
				try {
					ArrayList<UserInfoDto> list = userdao.getUserinfo(c_id);
						jb.put("c_id", list.get(0).getC_id());
						jb.put("c_index", list.get(0).getC_index());
						jb.put("c_email", list.get(0).getC_email());
						jb.put("c_name", list.get(0).getC_name());
						String phoneSub = list.get(0).getC_phone();
						System.out.println(phoneSub.substring(0,3));
						System.out.println(phoneSub.substring(3,7));
						System.out.println(phoneSub.substring(7,11));
						String phone = phoneSub.substring(0,3) + "-"+phoneSub.substring(3,7) + "-"+ phoneSub.substring(7,11);
						jb.put("c_phone", phone);
						jb.put("nickname", list.get(0).getNickname());						
						fjb.put("userInfo",jb);
						System.out.println(fjb.toString());
								
					
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			
				
				return fjb.toString();
			}
			
			@RequestMapping("/android/review")
			public @ResponseBody String uploadOk(HttpServletRequest request) throws Exception {
				System.out.println("board");
				int size = 1024 * 1024 * 10;
				String file = "";
				String oriFile = "";
				
				JSONObject obj = new JSONObject();
				
				try{
					String path = ResourceUtils
								.getFile("classpath:static/upload").toPath().toString();
					
					ClassPathResource classPathResource = new ClassPathResource("static/upload");

					System.out.println("111111111111111111" + classPathResource.exists());
					MultipartRequest multi;

//					try (InputStream is = new BufferedInputStream(classPathResource.getInputStream())) {
//						System.out.println("안녕");
//						multi = new MultipartRequest(request, is.toString(), size, "UTF-8", new DefaultFileRenamePolicy());
//					}
					
					
					multi = new MultipartRequest(request,path, size, "UTF-8", new DefaultFileRenamePolicy());
					System.out.println(path);
					Enumeration files = multi.getFileNames();
					if(files.hasMoreElements()) {
						String str = (String)files.nextElement();
						file = multi.getFilesystemName(str);
						oriFile = multi.getOriginalFileName(str);
						
					}
				
					
					System.out.println(file);
					System.out.println(oriFile);
					
					String m_number = multi.getParameter("m_number");
					System.out.println(m_number);
					String r_name = multi.getParameter("r_name");
					System.out.println(r_name);
					String nickname = multi.getParameter("nickname");
					System.out.println(nickname);
					String c_id = multi.getParameter("c_id");
					System.out.println(c_id);
					String contents = multi.getParameter("contents");
					System.out.println(contents);
					String grade = multi.getParameter("grade");
					System.out.println(grade);
					
					
					if (file == null) {
						obj.put("fail", new Integer(2));
						obj.put("desc", "file null...");
					} else {
						obj.put("success", new Integer(1));
						obj.put("desc", "성공");
						int review = testdao.review(m_number,r_name,c_id,contents,nickname,grade,
								file,oriFile);
						System.out.println(review);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					obj.put("success", new Integer(0));
					obj.put("desc", "업로드 실패");
				}
				return obj.toJSONString();
			}
			@RequestMapping("/notice/download")
			public ResponseEntity<Object> download(HttpServletRequest request) {
				System.out.println("Controller:File Download");
			
				try {
					String path = ResourceUtils
							.getFile("classpath:static/upload").toPath().toString();
	
				
					String fName = request.getParameter("fName");
					System.out.println("filename = " +   fName);
//					
				
//					out = response.getOutputStream();
					String downFile = path + "/" + fName;
					Path f = Paths.get(downFile);
					System.out.println("downFile = " +   downFile);
					File file = new File(downFile);
					HttpHeaders headers = new HttpHeaders();
					headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());
//					response.setHeader("Cache-Control", "no-cache");
//					response.addHeader("Content-disposition","attachment; fileName=" + fName);
//					in = new FileInputStream(f);
					Resource resource = new InputStreamResource(Files.newInputStream(f));
//					byte[] buffer = new byte[1024 * 8];
//					while(true) {
//						
//						int count = in.read(buffer);
//						
//						if(count == -1)
//						{
//							break;
//						}
//						out.write(buffer,0,count);
//						
//					}
//					in.close();
//					out.close();
					return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
				} catch (IOException e) {
					e.printStackTrace();
					return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
				
				}finally {
					
				}
	
		
				
			}
			@RequestMapping("/android/my_siren")
			public @ResponseBody String mysiren(HttpServletRequest request, Model model) {
				System.out.println("Controller:CustomerResList");
				String c_id = request.getParameter("c_id");
				String customerResList = rservice.getCustomerSiren(c_id);
				return customerResList;
			}

}
