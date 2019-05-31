package com.macro.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class util {

	final Logger logger = LoggerFactory.getLogger(util.class);

	@RequestMapping("fileUpload")
	public String fileUpload(@RequestParam("file")CommonsMultipartFile file) throws IOException{
		logger.info("fileName:"+file.getOriginalFilename());

		File filePath = new File(ResourceUtils.getURL("classpath").getPath());
		File upload = new File(filePath.getAbsolutePath(),"static/file/upload/");
		if (!upload.exists())
			upload.mkdir();
		String path = upload.getAbsolutePath() + new Date().getTime() + file.getOriginalFilename();

		File newFile = new File(path);
		file.transferTo(newFile);
		return "/success";
	}

}
