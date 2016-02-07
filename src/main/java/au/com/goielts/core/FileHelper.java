package au.com.goielts.core;

import org.springframework.stereotype.Component;

import au.com.goielts.model.UploadedFile;

@Component
public class FileHelper {
	
	public String getIconLink(UploadedFile file){
		String url = "/download/"+file.getId();
		String iconClass;
		switch(file.getExtension()){
			case "doc":
				iconClass = "fa-file-word-o";
				break;
			case "docx":
				iconClass = "fa-file-word-o";
				break;
			case "pdf":
				iconClass = "fa-file-pdf-o";
				break;
			default :
				iconClass = "fa-file-o";
		}
		String link = String.format("<a class=\"download-file\" href=\"%s\"><i class=\"fa fa-fw %s\"></i>%s</a>", url, iconClass ,file.getFileName());;
		return link;
	}
}
