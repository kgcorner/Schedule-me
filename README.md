
[![Build Status](https://app.travis-ci.com/kgcorner/Schedule-me.svg?branch=master)](https://app.travis-ci.com/kgcorner/Schedule-me)
[![codecov](https://codecov.io/gh/kgcorner/Schedule-me/branch/master/graph/badge.svg?token=47kLZJJuJ8)](https://codecov.io/gh/kgcorner/Schedule-me)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.dealsdelta%3Aschedule-me&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.dealsdelta%3Aschedule-me)
# Schedule-me
Schedule-me is a small scheduling platform that let's you create monitor and run jobs. It's a barebone ready to use framework that comes with a dummy job.

# How To use
In order to use Schedule-me platform, you need to create [JobKind](https://github.com/kgcorner/Schedule-me/blob/master/backend/src/main/java/com/dealsdelta/scheduleme/dtos/IJob.java#L15) or required type and implements a corresponding [JobProcessor](https://github.com/kgcorner/Schedule-me/blob/master/backend/src/main/java/com/dealsdelta/scheduleme/processors/JobProcessor.java) for the same.

# Sample Job processor 
A dummy email job processor has been created for reference 

# schedule-me-app
schedule-me-app is a front-end-app that gives an easy to use interface in order to schedule, monitor, run and manage jobs.

### Dashboard
![Dashboard](https://i.ibb.co/TmWwPW0/schedule-me.png)

### Job Forms
![Job Form](https://serving.photos.photobox.com/7033554497f49a76b9c1a1ba6153b5a16d2bb6630c7e04913bcf7b9697238c0b5b830847.jpg)

### Job Audit
![Job Audit](https://serving.photos.photobox.com/91816878199c37015bd637db4de8a982552d0387e60ad2b3f7cbeda2a3840cd6fa60408e.jpg)


