package telran.java47.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.post.dto.DatePeriodDto;
import telran.java47.post.dto.NewCommentDto;
import telran.java47.post.dto.NewPostDto;
import telran.java47.post.dto.PostDto;
import telran.java47.post.service.PostSrvice;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController  {
	
	final PostSrvice postSrvice;

	@PostMapping("/post/{author}")
	public PostDto addNewPost(@PathVariable String author,@RequestBody NewPostDto newPostDto) {
		
		return postSrvice.addNewPost(author, newPostDto);
	}

	@GetMapping("/post/{id}")
	public PostDto findPostById(@PathVariable String id) {
		
		return postSrvice.findPostById(id);
	}

	@DeleteMapping("/post/{id}")
	public PostDto removePost(@PathVariable String id) {
		
		return postSrvice.removePost(id);
	}

	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id,@RequestBody NewPostDto newPostDto) {
		
		return postSrvice.updatePost(id, newPostDto);
	}

	@PutMapping("/post/{id}/comment/{author}")
	public PostDto addCommentDto(@PathVariable String id,@PathVariable String author,@RequestBody NewCommentDto newCommentDto) {
		
		return postSrvice.addCommentDto(id, author, newCommentDto);
	}

	@PostMapping("/post/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addLike(@PathVariable String id) {
		postSrvice.addLike(id);
		
	}

	@GetMapping("/post/author/{author}")
	public Iterable<PostDto> findPostByAuthor(@PathVariable String author) {
		
		return postSrvice.findPostByAuthor(author);
	}

	@PostMapping("/posts/tags/")
	public Iterable<PostDto> findPostByTags(@RequestBody List<String> tags) {
		
		return postSrvice.findPostByTags(tags);
	}

	@PostMapping("/posts/period")
	public Iterable<PostDto> findPostByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
		
		return postSrvice.findPostByPeriod(datePeriodDto);
	}

}
