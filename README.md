# 🖥WatchIt-DB-Project🖥
> This is a project to deal with big data by using appropriate indexes in the database & dynamic queries.    
> It is a console project implementing functions in OTT(over-the-top) services.   
> It uses large amount of dummy data created instead of real data used in current OTT services.    
>    
> 대용량 데이터 처리를 위한 DB 및 Transaction을 설계 및 구현한 프로젝트입니다.    
> 콘솔 프로그램이며, 기본적인 OTT 서비스를 위한 기능들을 포함하고 있습니다.   
> 서비스 기능 구현을 위해 대용량 더미 데이터를 만들어 사용하였습니다. 

## 🌱Project Introduction🌱
### Goal 
- To deal with __big data__ (ex. over 1000000 data) we need to think of a way to effectively process them in the database 
- This could be done by using __appropriate indexs__ in relational databases   

### Service  
- Login/ Register (로그인/회원가입) 
- Buy tickets (이용권 결제)
- Multiple profiles per user (한 사용자에게 여러개의 프로필 할당)
- Show all contents to user (모든 컨텐츠 보여주기)
- Show contents by genre (장르별 컨텐츠 보여주기)
- Search contents / See user search history (컨탠츠 검색 및 사용자 검색기록 보기)
- View content specific information (컨텐츠 상세 정보 보기)
- Rate content (컨텐츠 별점 주기)
- Add content to my favorites (내 찜 목록에 컨텐츠 추가) 

## 💻Installation and Setup💻
To begin using this template, choose one of the following options to get started:
* [Fork, Clone, or Download on GitHub](https://github.com/cindia3704/WatchIt-DB-Project)  
* Open the project through Intellij IDEA  
* Run "Main.java" to start using the program

To set up the database with pre-defined SQL statements, please refer to the file below:
* [See SQL](https://github.com/cindia3704/WatchIt-DB-Project/blob/main/SQL/watchIt.sql)  

## 📝Requirements📝
- Java Version 11 
- MySQL Version 8.0.23
- MySQLWorkBench Version 8.0.20

## 🗂Project Directories 🗂
```bash
├── dao
│   ├── ContentCommentDao.java
│   ├── ContentDao.java
│   ├── MyContentDao.java
│   ├── OrdersDao.java
│   ├── RatingDao.java
│   ├── SearchHistoryDao.java
│   ├── TicketDao.java
│   ├── UserDao.java
│   └── UserProfileDao.java
├── entity
│   ├── ContentComment.java
│   ├── Content.java
│   ├── LoggedInUser.java
│   ├── MyContent.java
│   ├── Orders.java
│   ├── Rating.java
│   ├── SearchHistory.java
│   ├── Ticket.java
│   ├── User.java
│   └── UserProfile.java
├── enums
│   ├── ContentGenre.java
│   ├── ContentType.java
│   ├── RateStatus.java
│   ├── TicketType.java
│   └── UserStatus.java
└── Main.java
``` 
## 📙Project Functions📙
|**Image**|**Description**|   
|:----:|:----:| 
|![image](https://user-images.githubusercontent.com/52744390/130349248-a277bd0c-950f-4b0a-9928-b04d4d5f7ef2.png)|__Welcome page__|\
|![image](https://user-images.githubusercontent.com/52744390/130349337-858e1e55-358b-4094-9ba9-ceecf78ccea8.png)|__Register__|
|![image](https://user-images.githubusercontent.com/52744390/130349302-00c017b6-02a8-432a-80e1-eb944e875652.png)|__Login__|
|![image](https://user-images.githubusercontent.com/52744390/130349310-0fd8d3ee-ba89-4272-ac95-c240cb7a219d.png)|__Choose User Profile__|
|![image](https://user-images.githubusercontent.com/52744390/130349324-0bcc1943-d40b-4939-be90-ea4058d14f8d.png)|__Buy Ticket__|
|![image](https://user-images.githubusercontent.com/52744390/130349352-746fa82a-ab15-40c1-9d9c-15900efafcea.png)|__Main Page__|
|![image](https://user-images.githubusercontent.com/52744390/130349365-0ca08e0b-dc73-4759-b382-072aebac23c0.png)|__See Content by Type__</br>Choose content type|
|![image](https://user-images.githubusercontent.com/52744390/130349388-3fcb484b-a949-41db-b917-6a3eef6f9d70.png)|__See Content by Type__</br>List of all contents|
|![image](https://user-images.githubusercontent.com/52744390/130349400-2b0b5f64-c865-4b1b-848a-d6577734e316.png)|__See Content by Genre__</br>Choose content genre|
|![image](https://user-images.githubusercontent.com/52744390/130349418-68420272-1c02-4377-9885-39db89b0d31e.png)|__See Content by Genre__</br>List of all contents|
|![image](https://user-images.githubusercontent.com/52744390/130349429-29ff3854-f31f-4d92-9d24-907a72a86dfa.png)|__See My Content List__|
|![image](https://user-images.githubusercontent.com/52744390/130349435-0a66d2b6-0460-41cc-93d6-97233fa86ad0.png)|__See Content Detail__|
|![image](https://user-images.githubusercontent.com/52744390/130349445-f03b619d-5003-49f4-9662-2c5090c5c330.png)|__See Search History__|
|![image](https://user-images.githubusercontent.com/52744390/130349459-8e644dc8-d0e5-49fe-b0ee-38a2c6404595.png)|__See My Comment List</br>& Change My Comment__|


## 📌ERD📌
<img width="853" alt="스크린샷 2021-08-22 오후 4 39 25" src="https://user-images.githubusercontent.com/52744390/130346648-7e21278d-ac1c-4091-9367-f640b1c32131.png">

## ✨Connecting to Database✨  
* Database is connected by using jdbc 
* To use this code and connect to your local database, change the "USERNAME" & "PASSWORD"
* You may need to change the URL if your schema name is not "WatchIt"
```java
        Connection conn = null;

        final String USERNAME = "ENTER_YOUR_MYSQL_USERNAME";
        final String PASSWORD = "ENTER_YOUR_MYSQL_PASSWORD";
        final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Class not found!!");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection failed!!");
        }
```


## 📊Creating Big Data Example📊
__1. Create a method making dynamic SQL statements__   
➡️ Use PreparedStatement to create dynamic queries & use set methods to insert data for each property
```java
  public static void insertComments(ContentComment contentComment, Connection conn) throws SQLException {
        String sqlStmt = "insert into content_comment values(?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,contentComment.getId());
            pStmt.setString(2,contentComment.getComment());
            pStmt.setInt(3,contentComment.getUserProfileId());
            pStmt.setInt(4,contentComment.getContentId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
```
   
__2. Create method for making Entities__    
__3. Call the method made in (1) and provide entity made in (2) & connection instance parameters__ 
```java
  private static void makeRandomContents() throws SQLException {
        for(int i =1;i<=5;i++){
            ContentDao contentDao = new ContentDao();
            Content content = new Content();
            content.setId(i);
            content.setContentType(getContentType(getRandomIndex(0,4)));
            content.setContentGenre(getContentGenre(getRandomIndex(0,12)));
            content.setTitle(getRandomString(5,10));
            content.setYear(getRandomIndex(1980,2022));
            content.setDescription(getRandomString(30,300));
            content.setPoster("https://watchIt.com/"+getRandomString(5,20)+".jpg");
            content.setVideo("https://watchIt.com/"+getRandomString(5,20)+".mp4");
            content.setTotalRateScore(getRandomDouble(0.0,5.0));
            content.setAgeLimit(getRandomIndex(0,20));
            contentDao.insertContent(content);
        }
    }
```
__Result__   
➡️ 100000 data created in database 
![image](https://user-images.githubusercontent.com/52744390/130347998-80b03ffe-4b08-4185-9ce0-1553029cce23.png)


## Creating Indexs 
* Function: Showing all contents by type 
  * Index needed in Content table
  * SQL statement for creating index: 
    ```sql
    CREATE INDEX idx_type_content ON WatchIt.content(type);
    ```
![image](https://user-images.githubusercontent.com/52744390/130348470-8cc695c7-9668-419c-bc84-772a33b8dc0a.png)


* Function: Showing contents in user's my content list that the user didn't rate  
  * Index needed in MyContent table
  * SQL statement for creating index: 
    ```sql
    CREATE INDEX idx_profile_id_rating_status ON WatchIt.my_content(user_profile_id,rating_status);
    ```
* Function: Change rate status of a certain content in my content list 
  * Index needed in MyContent table
  * SQL statement for creating index: 
    ```sql
    CREATE INDEX dx_profile_content ON WatchIt.my_content(user_profile_id,content_id);
    ```
![image](https://user-images.githubusercontent.com/52744390/130348607-bc145bcf-4a4c-4fff-afd3-2cae73066a9a.png)

## Transaction Example 
* Function: 
  * Update user ticket payment & status when "INACTIVE" user logged in 
  * 사용자 로그인 시 사용자가 비활성화 된 사용자의 이용권 구매 및 status 업데이트 
* SQL statements in transaction: 
  * S1-1 : 
    ```sql  
          SELECT * FROM user WHERE username = ? and password = ? ;
     ```
  * S1-1 Explanation:
    * Check if user with username & password exists 
    * 회원가입 된 사용자 중, 사용자 입력으로 들어온 username & password를 갖는 사용자가 있는지 확인 후 있다면 해당 사용자 조회

  * S1-2: 
    ```sql  
      SELECT * FROM ticket;  ( 정적 SQL ) 
    ```
  * S1-2 Explanation: 
    * If user status is INACTIVE, redirect to payment page & show all ticket types 
    * 사용자가 비활성화 된 사용자인 경우 이용원 구매 페이지로 이동하여 이용권을 모두 보여줌 

  * S1-3: 
    ```sql
      SELECT MAX( id ) as total FROM orders;
    ```
  * S1-3 Explanation:
    * If user bought a new ticket, get max id from order table
    * 사용자가 이용권을 구매하면 orders 테이블에 레코드를 하나 생성해야 하는데 이때 id값 설정을 위해 현재 orders 테이블에 레코드 중 id 칼럼의 최대값을 조회하여 이보다 1만큼 큰 수를 새 레코드의 id 값으로 설정한다. 

  * S1-4: 
    ```sql
      INSERT INTO orders values(?,?,?,?,?);
    ```
  * S1-4 Explanation:
    * If user bought a new ticket, add a record in order table
    * 사용자가 새 이용권을 구매했다면 order 테이블에 레코드 하나 생성 

  * S1-5: 
    ```sql
      UPDATE user SET status = ? WHERE id = ?;
    ```
  * S1-5 Explanation: 
    * Update user status to ACTIVE
    * 사용자가의 새 이용원 구매가 성공적이며 사용자의 상태를 비활성화에서 활성화로 변경한다. 이때 동적 매개변수 id의 값은 S1-1에서 반은 결과 셋의 데이터 값의 일부이다. 

