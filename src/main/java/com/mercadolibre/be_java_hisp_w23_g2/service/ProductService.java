package com.mercadolibre.be_java_hisp_w23_g2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import com.mercadolibre.be_java_hisp_w23_g2.dto.PostDTO;
import com.mercadolibre.be_java_hisp_w23_g2.entity.Post;
import com.mercadolibre.be_java_hisp_w23_g2.entity.User;
import com.mercadolibre.be_java_hisp_w23_g2.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w23_g2.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final IUserRepository userRepository;

    public ProductService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MessageDTO addPost(PostDTO postDTO) {
        // Check that all parameters have been sent
        if (postDTO.getUserId() == 0 || postDTO.getDate() == null ||
            postDTO.getProduct().getId() == 0 || postDTO.getProduct().getName() == null ||
            postDTO.getProduct().getType() == null || postDTO.getProduct().getBrand() == null ||
            postDTO.getProduct().getColor() == null || postDTO.getProduct().getNotes() == null ||
            postDTO.getCategory() == null || postDTO.getPrice() == 0.0) {
            throw new BadRequestException("The publication data entered is not correct.");
        }

        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.convertValue(postDTO, Post.class);

        // Check that the user exists
        User user = userRepository.findUserById(post.getUserId());
        if (user == null) {
            throw new BadRequestException("The user does not exist.");
        }

        // Check that there is no product with this id
        List<Post> postsUser = user.getPosts();
        Optional<Post> postExist = postsUser.stream()
                                    .filter(p -> p.getProduct().getId() == post.getProduct().getId()).findFirst();
        if (postExist.isPresent()) {
            throw new BadRequestException("The product id already exists.");
        }

        // In case of passing the validations, I add the post to the list
        userRepository.addPost(user, post);

        return new MessageDTO("Publication successfully added.");
    }

}