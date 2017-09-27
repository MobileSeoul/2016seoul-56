# EASYGRAB
서울시 택시운행 데이터를 분석하여 사용자는 택시 타기 좋은 위치, 운전사는 손님 태우기 좋은 위치를 탐색하는 앱니다. :)
(2015/01 ~ 2016/04 서울시 택시운행 데이터 기준) 

## 주요기능
- 현재 위치 탐색하기
- 택시 타기 좋은 위치 탐색하기

## 주요화면
- 초록색 - 택시 타기 가장 좋은 위치 / 손님 태우기 좋은 위치
- 노랑색 - 그냥 그런 위치
- 빨강색 - 택시 타기 가장 안 좋은 위치 / 손님 태우기 가장 안 좋은 위치

![demo](./img/demo.gif)

## 구현
- 코틀린 언어로 구현함
- [Clean Architecture](https://github.com/android10/Android-CleanArchitecture) 를 참고하여 프로젝트의 틀을 잡고 Rxjava, Retrofit2 로 비지니스 로직을 구현함
- Dagger2 을 활용하여 클래스 간 의존성 주입함  

## 디렉토리 구조
- src  
    - api : Api 서버 통신
    - di : 의존성 주입 (Dagger2 - Component, Module)
    - usecase : 비지니스 로직 (택시 타기 좋은 위치 탐색하)
    - presenter : Presenter 레이어
    - view : View 레이어 (안드로이드 액티비티, 뷰 컴포넌트)
    
## 구성도
![architecture](./img/architecture.png)

- API 서버 : https://github.com/babjo/EasyGrabApi
- 클라이언트 : https://github.com/babjo/EasyGrab