# ⚾ KBO-Ticketing ⚾

## 📌 프로젝트 소개

![kbo](https://github.com/f-lab-edu/kbo-ticketing/assets/59499600/2af013e7-c460-45dd-8123-d9c1daa9edbf)

`KBO 야구 예매 사이트(KBO-Ticketing)`입니다. 백엔드 개발에 집중하기 위해
프론트엔드는 [KBO-Ticketing 프로토타입](https://ovenapp.io/view/LGQwJohSa02Ln3DJ18z8umKmDUtWxCww/) 을 활용하였습니다.
프로젝트 상세 내용은 [Wiki](https://github.com/f-lab-edu/kbo-ticketing/wiki) 에서 확인할 수 있습니다.

## 📌 프로젝트 목표

- `한국시리즈`과 같이 대용량 트래픽 상황을 고려해 기능을 구현하는 것
- 객체 지향 원리를 토대로 개발하는 것
- 지속적으로 성능 개선 및 리팩토링하는 것

## 📌 기술스택

`Java 17`, `Spring boot`, `Mysql`, `Mybatis`, `Redis`, `Docker`, `Naver Cloud Platform`, `nGrinder`, `Pinpoint`

## 📌 작업기간

`2024.03.12` ~

## 📌 기술적 issue 해결 과정

- [Redis Lua Script vs Java synchronized: 동시성 테스트를 통한 성능 비교](https://azelhhh.tistory.com/115)
- [Redis Lua Script 실제로 Atomic할까?](https://azelhhh.tistory.com/116)
- [유닛테스트 중 @Value가 null일때 feat. @Value의 주입 시점](https://azelhhh.tistory.com/117)
- [nGrinder와 Pinpoint로 성능테스트 및 개선하기 (1) - 환경 구축](https://azelhhh.tistory.com/118)
- [nGrinder와 Pinpoint로 성능테스트 및 개선하기 (2) - 병목지점 파악](https://azelhhh.tistory.com/119)
- [nGrinder와 Pinpoint로 성능테스트 및 개선하기 (3) - Scale out](https://azelhhh.tistory.com/120)

## 📌 서버 구조도

<img width="800" alt="server2" src="https://github.com/f-lab-edu/kbo-ticketing/assets/59499600/b535e355-2253-4cfd-b371-dae2ee48ceed">

## 📌 DB ERD

<img width="800" alt="erd" src="https://github.com/f-lab-edu/kbo-ticketing/assets/59499600/e5a5b8e4-30f2-4cad-8c10-dfbe90a68236">