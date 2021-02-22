package com.banger.bangerapi;

import com.banger.bangerapi.Models.Review;
import com.banger.bangerapi.Repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void findById() {
        Review review=new Review("testReviewer","testReview","testDate");
        review= testEntityManager.persistAndFlush(review);
        Review foundReview = reviewRepository.findById(review.getId()).get();
        assertEquals(foundReview.getId(), review.getId());
    }

    @Test
    public void testReviewListIsNotNull(){
        List<Review> contacts=reviewRepository.findAll();
        assertThat(contacts).size().isGreaterThan(0);
    }

    @Test
    public void testDelete(){
        Review review1=new Review("testReviewer","testReview","testDate");
        review1= testEntityManager.persistAndFlush(review1);
        Review review=reviewRepository.findById(review1.getId()).orElse(null);
        reviewRepository.deleteById(review.getId());
        Review foundReview=reviewRepository.findById(review1.getId()).orElse(null);
        org.junit.jupiter.api.Assertions.assertNull(foundReview);

    }


}
