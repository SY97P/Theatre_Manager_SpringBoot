# 🍿 극장 관리 시스템 - Theatre Manager

## 🛎️ Summary

### 🧩 Requirements

> 혜화동에 차린 나의 극장.<br>
> 시설 투자를 잘 했기 때문일까? <br>
> 내 극장에서 공연을 올리고 싶어하는 극단이 많다. <br>
> 극단 요청에 따라 공연 정보를 저장하고, 삭제하는 서비스가 있었으면 좋겠다. <br>
> 또, 관객들은 올려진 공연을 보기 위해 티켓을 구매하는 서비스도 있었으면 좋겠다.

- for 극단
    - 공연 정보를 form 에 입력해서 정보 추가
    - 공연 정보 삭제
- for 관객
    - 등록된 공연 정보 선택해 티켓 구매
    - 티켓 구매 수량 선택
    - 공연 기간이 아닌 날짜를 선택했을때 알림
    - 최종 구매한 티켓에 대해서 결제

### 🧬 Flow Chart

![flow_chart.png](summary%2Fflow_chart.png)

### 🗂️ Class Diagram

![class_diagram.png](summary%2Fclass_diagram.png)

### 🪢 Domain Relation

![domain_relation.png](summary%2Fdomain_relation.png)

### 🍔 ERD

![erd.png](summary%2Ferd.png)

### 🕹️ 사용한 기술

- Spring Boot
    - ~~JdbcTemplate~~
        - JPA 적용하면서 제거했습니다.
    - JPA
- Jasypt
- Mysql Database
- H2 Database (for Test)
- ~~mapstruct~~
    - 의존성에 구애 받지 않고 개발하는 것을 목표로 제거했습니다.

## ⏳ Retrospect

### 🎾 클래스 설계

<details>
<summary>클래스 간 관계</summary>
<div markdown="1">

    이번 개인 프로젝트 목표는 짜임새 있는 클래스 설계였습니다.   
    그동안 경험, 습득, 상상했던 클래스 설계를 처음부터 녹여보고 싶었습니다.   
    
    1. 요구사항을 기능별로 분리한다.
    - 영속성 관련 로직을 시점으로 기능을 분리했습니다.
    - 영속성 관련 로직이 가장 덜어낼 것이 없는 최소성을 가진 로직으로 만들고 싶었습니다. 
    2. 필요한 모든 개념을 그림으로 그린다.
    - performance(공연), ticket(티켓), ticket order(티켓주문) 세 개의 개념이 필요했습니다.
    - 주문이 포괄적인 개념이라고 생각해, `티켓` 이라는 비즈니스 개념에 포함된 `주문` 으로 설정했습니다.
    3. 개념 간의 관계를 그림으로 그린다.
    - ERD 보다는 도메인 클래스 간 연결관계를 명확하게 만들고 싶었습니다.
    - 구현 순서를 명확하게 정하고 싶었고, 구현 영역 중 어떤 부분이 다른 개념에 영향을 줄 수 있는지 예상하고 싶었습니다.

</div>
</details>

<details>
<summary>최대한 작은 범위 도메인 설정</summary>
<div markdown="1">

    필요한 모든 개념을 더 이상 쪼갤 수 없는 개념으로 잘게 쪼개고 싶었습니다.   
    그동안 사양변경에 영향이 많은 코드를 작성하면서 느꼈던 분노와 울분을 극복하는 좋은 방법이라고 생각했기 때문입니다.
    
    `주문` 이라는 개념이 포괄적이라고 생각했습니다. 
    `주문` 안에 `티켓` 이 있는 구조라면, `티켓`이외의 개념이 추가할 수록 거대한 클래스가 될 거라고 생각했기 때문입니다.
    
    그래서 `티켓` 이라는 개념에 속한 `티켓 주문` 이라는 클래스로 만들어 새로운 개념이 추가되어도 기존의 로직은 변경하지 않도록 설계했습니다. 
    
    클래스 안에서도 최소성이 유지되었으면 좋겠다고 생각해 원시값 포장을 적극적으로 채용했습니다.

</div>
</details>

<details>
<summary>원시값 포장</summary>
<div markdown="1">

    - 도메인 클래스 필드를 모두 원시값으로 포장해 사용했습니다.
    - 도메인 클래스에서 모든 필드에 대한 유효성 검사를 하지 않고, 개별 필드가 자신의 유효성을 판단하고 잘못된 인스턴스 생성을 방지하기 때문에
      낮은 결합, 높은 응집을 만족할 수 있었습니다.
    - 특정 필드에 대한 디버깅이나, 타입변경에도 수정할 곳이 적어, 개발 실수를 미연에 방지하는 좋은 장치인 것 같습니다.

</div>
</details>

<details>
<summary>top-down? bottom-up?</summary>
<div markdown="1">

    설계 당시에는 bottom-up 방식을 채택했습니다.
    작은 개념부터 큰 개념 순으로 구현하는 것입니다.
    
    체감했던 장점이 있습니다.   
    프로그램 근간이 되는 작은 부분만 고려하여 프로그램을 작성하니,
    나중에 프로그램 규모가 커진 시점에서도 원소 부분에 대한 믿음을 가지고 구현할 수 있었습니다.   
    단점도 있었습니다.   
    설계 단계에서 미처 예상하지 못한 부분이 있어 구조 변경이 필요한 경우,   
    이전까지 구현했던 내용의 대부분이 의미가 없어져 제거하고 새로 작성해야 하는 경우가 있었습니다.
    
    다음부터는 도메인 개념을 먼저 만들고,    
    컨트롤러 로직을 만들면서 필요한 기능이 설계에서 충분히 고려했는지 판단한 후에   
    레포지토리 레벨 구현을 하는 방식으로 작업하려고 합니다.

</div>
</details>

### 🍔 테스트 코드

<details>
<summary>테스트 코드에도 중복이 있는가</summary>
<div>

    저를 딜레마에 빠지게 한 고민입니다. 
    
    동료분들과도 많은 이야기를 나눠봤지만 각자 개인의 의견이 달라, 어떤 것이 정답이라고 할 수 없는 부분인 것 같습니다. 
    
    결론부터 말씀드리면, 지금의 저는 테스트 코드에도 중복이 있다고 생각합니다.
    특히 테스트 데이터가 중복을 심화하는 주 원인이라고 생각합니다.
    
    필요한 클래스의 필드가 변경될때마다 관련 로직 수정보다 테스트 데이터 수정에 훨씬 많은 리소스가 들어갔습니다. 
    그래서 이번엔 테스트 데이터를 모은 테스트 데이터 클래스를 만들고 이를 활용하는 방식으로 사용했습니다.
    
    제가 객체지향과 선언형을 공부하면서 느꼈던 가장 핵심은 유지보수가 편하고, 영향이 적은 코드를 작성하는 것입니다.
    테스트 데이터를 모든 테스트 클래스마다 새로 작성하는 것이 배움을 역행하는 거라고 생각합니다.
    
    하지만, 테스트 데이터 클래스를 따로 만드니
    
    테스트 FIRST 속성 중에서 isolated 속성을 위반한 것 같은 느낌도 들고,
    하나의 테스트 데이터에 너무 많은 테스트 메소드가 의존하는게 아닌가 라는 생각이 들었습니다.
    또한 @MethodSource 인자로 사용되는 리플랙션이 다른 클래스의 함수를 사용하는 것처럼 포괄적으로 사용해도 좋은 것인지에 대한 의문도 들었습니다.
    
    결론은 정했지만, 앞으로도 계속 고민할 문제라고 생각합니다.

</div>
</details>

<details>
<summary>테스트 대상</summary>
<div>

    이전 과제를 하면서 테스트 관련 피드백에 항상 있던 내용이 있었습니다.
    
    '이 테스트가 꼭 필요한가요?'
    
    테스트 대상을 고려하지 않고 테스트 작성을 했다고 이해했습니다. 
    
    이번 테스트에서는 `로직`과 `데이터 전달` 책임을 인식해 테스트를 작성했습니다. 
    
    컨트롤러는 `데이터 전달`
    서비스와 레포지토리는 `로직` 으로 구분하고, 로직 관련 테스트에 집중했습니다.
    
    또한 서비스에서 필요한 로직을 구현할 수 있도록 하고 싶었기 때문에, mock 객체를 사용하지 않고, 
    
    의존성을 주입받아 테스트했습니다.

</div>
</details>

### 📯 커스텀 예외

<details>
<summary>커스텀 예외가 필요한 이유</summary>
<div>

    이전까지 RuntimeError 를 상속하는 커스텀 예외를 만들어 사용했었습니다.
    프로그램에서 발생하는 모든 예외를 대처할 수 있는 Common 예외였습니다.
    
    이렇게 하니, unchecked exception 까지 굳이 붙잡아서 커스텀 예외로 다시 던지는게 되었습니다.
    
    불필요한 과정이기도 하고, 자바에서 기본적으로 제공하는 예외를 사용하는게 더 범용성 있을거라 판단해,    
    이번 프로젝트에서는 사용하지 않으려고 했습니다. 
    
    하지만, 직접 RuntimeException을 코드에서 던지는 것보다 커스텀 예외를 던져서 처리하는 것이 더 바람직하다는 IDE 조언에 따라,
    이번 프로젝트에서도 커스텀 예외를 만들어 사용했습니다. 
    
    
    이번 프로젝트에서는 지난번 커스텀 예외처럼 Common 개념의 유일한 예외가 아닌, 유형별로 나눠 만들었습니다.
    
    이름이 같은 예외 클래스를 인자로 넘겨주는 ErrorMessage에 따라서 다른 예외로 처리하고 싶지 않았기 때문입니다.

</div>
</details>

### 💊 스프링 프레임워크 구조 (MVC)

- 컨트롤러, 서비스, 레포지토리 각 계층 별로 의존관계를 갖는 데이터 모델을 분리

## 🖥️ React UI

### git hub link

[Theatre_Manager_React](https://github.com/SY97P/Theatre_Manager_React.git)

![main_mode.png](..%2F..%2FReactProjects%2Ftheatre_manager_react%2Fsummary%2Fmain_mode.png)
![reservation_mode.png](..%2F..%2FReactProjects%2Ftheatre_manager_react%2Fsummary%2Freservation_mode.png)
![theatre_mode.png](..%2F..%2FReactProjects%2Ftheatre_manager_react%2Fsummary%2Ftheatre_mode.png)
![ticket_box_mode.png](..%2F..%2FReactProjects%2Ftheatre_manager_react%2Fsummary%2Fticket_box_mode.png)
![db.png](summary%2Fdb.png)