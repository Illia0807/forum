package telran.java47.post.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.post.dao.PostRepository;
import telran.java47.post.dto.DatePeriodDto;
import telran.java47.post.dto.NewCommentDto;
import telran.java47.post.dto.NewPostDto;
import telran.java47.post.dto.PostDto;
import telran.java47.post.model.Comment;
import telran.java47.post.model.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostSrvice {
	
	final PostRepository postRepository;
	final ModelMapper modelMapper;
	

	@Override
	public PostDto addNewPost(String author, NewPostDto newPostDto) {
		 Post post = modelMapper.map(newPostDto, Post.class);
	        post.setAuthor(author);
	        Post savedPost = postRepository.save(post);
	        return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post= postRepository.findById(id).orElse(null);
		return modelMapper.map(post, PostDto.class);
				
	}

	@Override
	public PostDto removePost(String id) {
		Post post= postRepository.findById(id).orElse(null);
		postRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Post post= postRepository.findById(id).orElse(null);
		post.setTitle(newPostDto.getTitle());
		post.setContent(newPostDto.getContent());
		Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            for (String tag : tags) {
                post.addTag(tag);
            }
            
        }
        postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	
	}

	@Override
	public PostDto addCommentDto(String id, String author, NewCommentDto newCommentDto) {
		Post post= postRepository.findById(id).orElse(null);
		 Comment newComment = new Comment(author, newCommentDto.getMessage());
		 post.addComment(newComment);
		 postRepository.save(post);
		
		return  modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post= postRepository.findById(id).orElse(null);
		post.addLike();
		 postRepository.save(post);
		
	}

	@Override
	public Iterable<PostDto> findPostByAuthor(String author) {
		
		return  postRepository.findByAuthorIgnoreCase(author)
				.map(s->modelMapper.map(s, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostByTags(List<String> tags) {
	
		return postRepository.findByTagsInIgnoreCase(tags)
				.map(s->modelMapper.map(s, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostByPeriod(DatePeriodDto datePeriodDto) {
		
		return postRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
				.map(s->modelMapper.map(s, PostDto.class))
				.collect(Collectors.toList());
	}

}
