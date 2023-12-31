package telran.java47.post.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;



import telran.java47.post.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	Stream <Post> findByAuthorIgnoreCase(String name);
	 Stream<Post> findByTagsInIgnoreCase(List<String> tags);
	 Stream<Post> findByDateCreatedBetween(LocalDate fromDate, LocalDate toDate);
	

}
