package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Models.Review;
import com.banger.bangerapi.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/review")
@RestController
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestBody Review review){
        return  reviewService.addReview(review);
    }

    @GetMapping("/getAllReviews")
    private ResponseEntity<?> getAllReviews() throws Exception{
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

}
