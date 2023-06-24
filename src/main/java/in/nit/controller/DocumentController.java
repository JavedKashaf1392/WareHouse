package in.nit.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.nit.model.Document;
import in.nit.service.IDocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

	@Autowired
	private IDocumentService service;
	//1.show document page
	@GetMapping("/all")
	public String showDocs(Model model) {
		model.addAttribute("list",service.findIdAndName());
		return "Documents";
	}
	
	//2.save
	@PostMapping("/save")
	public String upload(@RequestParam Integer fileId,@RequestParam MultipartFile fileOb) {
		Document doc=null;
		doc = new Document();
		doc.setDocId(fileId);
		doc.setDocName(fileOb.getOriginalFilename());
		try {
			doc.setDocdata(fileOb.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.saveDocument(doc);
		return "redirect:all";
	}
	
	//3.download the uploaded document
	
	@GetMapping("/download/{id}")
	public void download(@PathVariable Integer id, HttpServletResponse resp) {
		Optional<Document>opt= service.getOneDocument(id);
		if(opt.isPresent()) {
			Document doc=opt.get();
			resp.setHeader("Content-Disposition","attachment;filename="+doc.getDocName());
			try {
				FileCopyUtils.copy(doc.getDocdata(),resp.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
