create database socialplatform;
use socialplatform;
create table article(
    articleId int primary key auto_increment,
    articleName varchar(200),
    body text,
    releaseTime Timestamp,
    numOfViews int
);
create table review(
    reviewId int primary key AUTO_INCREMENT,
    reviewContent varchar(200)
);

create table user(
    userId BIGINT primary key ,
    userName varchar(20),
    cv varchar(200),
    image mediumblob,
    password varchar(200)
);
create table likes(
    userId BIGINT,
    articleId int,
    foreign key (userId) references user(userId),
    foreign key (articleId) references article(articleId),
    primary key (userId,articleId)
);
create table writing(
    userId BIGINT,
    articleId int,
    foreign key (userId) references user(userId),
    foreign key (articleId) references article(articleId),
    primary key (userId,articleId)
);
create table comment(
    userId BIGINT,
    reviewId int,
    foreign key (userId) references user(userId),
    foreign key (reviewId) references review(reviewId),
    primary key (userId,reviewId)
);
create table contain(
    articleId int,
    reviewId int,
    foreign key (articleId) references article(articleId),
    foreign key (reviewId) references review(reviewId),
    primary key (articleId,reviewId)
);
create table subComment(
    domReview int,
    subReview int,
     foreign key (domReview) references review(reviewId),
     foreign key (subReview) references review(reviewId),
     primary key (domReview,subReview)
);



