package in.nit.service;

import java.util.List;
import java.util.Optional;

import in.nit.model.Document;

public interface IDocumentService {

	public void saveDocument(Document doc);
	public List<Object[]>findIdAndName();
	public Optional<Document> getOneDocument(Integer id);
}
