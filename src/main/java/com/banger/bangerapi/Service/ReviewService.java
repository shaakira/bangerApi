package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Review;
import com.banger.bangerapi.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(){

    }
    public ResponseEntity<String> addReview(Review review){
        Review review1=new Review();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd yyyy");
        Date date = new Date();
        review1.setReviewer(review.getReviewer());
        review1.setReview(review.getReview());
        review1.setDate(formatter.format(date));
        reviewRepository.save(review1);
        return new ResponseEntity<>("Successfully Submitted",HttpStatus.OK);
    }
    public List<Review> getAllReviews(){
        List<Review> reviews=reviewRepository.findAll();
        return reviews;
    }

}
