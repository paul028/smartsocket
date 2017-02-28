#include <Thread.h>
#include <ThreadController.h>
ThreadController controll = ThreadController();
  #include <SoftwareSerial.h>
SoftwareSerial esp(2,3);
String ssid ="DeVera_NONAT_Pabilonia";
String password="hindinamintoalam";

String data;
String server= "192.168.1.2";
String receive_state[3];
boolean state[]={LOW,LOW,LOW}; 
Thread* thread1 = new Thread();
Thread* thread2 = new Thread();
Thread* thread3 = new Thread();



void reset_wifi()
{
  esp.println("AT+RST");
  delay(1000);
  if(esp.find("OK") ) 
  Serial.println("Module Reset");
}
void connectWifi()
{
esp.println("AT+CWMODE=1");
esp.println("AT+CWJAP=""" +ssid+""",""" + password + "\"");
delay(4000);
  if(esp.find("OK")) 
  {
    Serial.println("Connected!");
  }
  else 
  {
  Serial.println("Cannot connect to wifi"); 
  }
}

void httppost (String uri, String data) 
{
esp.println("AT+CIPSTART=\"TCP\",\"" + server + "\",80");//start a TCP connection.
if( esp.find("OK")) {
Serial.println("TCP connection ready");
} delay(10);
String postRequest =
"POST " + uri + " HTTP/1.0\r\n" +
"Host: " + server + "\r\n" +
"Accept: *" + "/" + "*\r\n" +
"Content-Length: " + data.length() + "\r\n" +
"Content-Type: application/x-www-form-urlencoded\r\n" +
"\r\n" + data;
String sendCmd = "AT+CIPSEND=";//determine the number of caracters to be sent.
esp.print(sendCmd);
esp.println(postRequest.length() );
delay(10);
if(esp.find(">"))
{ Serial.println("Sending.."); esp.print(postRequest);
if( esp.find("SEND OK")) 
{ Serial.println("Packet sent");
// close the connection
esp.println("AT+CIPCLOSE");
}
}}
void receive_data() // for on off using app
{
    esp.println("AT+CIPSTART=\"TCP\",\"" + server + "\",80");
    delay(10);
    if (esp.find("OK"))
      {
        Serial.println("TCP Connection Ready.");
      }      String location ="/smartsocket/IOT/getdata.php";
             String postRequest = "GET " + location + " HTTP/1.0\r\nHost: "+server+"\r\n\r\n";
             esp.print("AT+CIPSEND=");
             esp.println(postRequest.length());
             delay(10);
             if (esp.find(">"))
                {
                   Serial.println("Sending...");
                   esp.println(postRequest);
                   esp.println();
                  if (esp.find("SEND OK"))
                  {
                    Serial.println("Packet sent");
                    Serial.println();
                    while (esp.available()) {
                    String tmpResp = esp.readString().substring(230,270);
                     receive_state[0]=tmpResp.substring(9,10);
                     receive_state[1]=tmpResp.substring(22,23);
                     receive_state[2]=tmpResp.substring(35,36);
                    }// close the connection
                    esp.println("AT+CIPCLOSE");
                 }
                  else
                  {
                    Serial.println("Connection failed");
                  }
            }
}

void switch_socket() // manual on off
{

   if(digitalRead(5)==1) // query new state to databae
 {
    while(digitalRead(5)==1)
    {
      
    }
    state[0] =!state[0]; 
  //  delay(100);
    digitalWrite(8,state[0]);
    digitalWrite(11,state[0]);
    if(state[0]==HIGH)
    {
      httppost("/smartsocket/IOT/update_plug1.php","socket_state=1&socket_id=1");
      delay(10);
       receive_data();
             delay(10);
    }
    else
    {
          httppost("/smartsocket/IOT/update_plug1.php","socket_state=0&socket_id=1");
                delay(10);
                 receive_data();
             delay(10);
    }
 }
   if(digitalRead(6)==1) // query new state to databae
 {
    while(digitalRead(6)==1)
    {
      
    }
    state[1] =!state[1]; 
    delay(100);
    digitalWrite(9,state[1]);
    digitalWrite(12,state[1]);
       if(state[1]==HIGH)
    {
      httppost("/smartsocket/IOT/update_plug1.php","socket_state=1&socket_id=2");
            delay(10);
             receive_data();
             delay(10);
    }
    else
    {
          httppost("/smartsocket/IOT/update_plug1.php","socket_state=0&socket_id=2");
                delay(10);
                 receive_data();
             delay(10);
    }
 }
   if(digitalRead(7)==1) // query new state to databae
 {
    while(digitalRead(7)==1)
    {
      
    }
    state[2] =!state[2]; 
    delay(100);
    digitalWrite(10,state[2]);
    digitalWrite(13,state[2]);
       if(state[2]==HIGH)
    {
      httppost("/smartsocket/IOT/update_plug1.php","socket_state=1&socket_id=3");
            delay(10);
            receive_data();
             delay(10);
    }
    else
    {
          httppost("/smartsocket/IOT/update_plug1.php","socket_state=0&socket_id=3");
                delay(10);
                 receive_data();
             delay(10);
    }
 }
 if(receive_state[0].equals("1"))
 {
    digitalWrite(8,HIGH);
    digitalWrite(11,HIGH);
    state[0]=HIGH;
 }
  if(receive_state[0].equals("0"))
 {
   digitalWrite(8,LOW);
    digitalWrite(11,LOW);
      state[0]=LOW;
 }
 if(receive_state[1].equals("1"))
 {
  digitalWrite(9,HIGH);
    digitalWrite(12,HIGH);
      state[1]=HIGH;
 }
  if(receive_state[1].equals("0"))
 {
  digitalWrite(9,LOW);
    digitalWrite(12,LOW);
      state[1]=LOW;
 }
 if(receive_state[2].equals("1"))
 {
  digitalWrite(10,HIGH);
    digitalWrite(13,HIGH);
      state[2]=HIGH;
 }
  if(receive_state[2].equals("0"))
 {
    digitalWrite(10,LOW);
    digitalWrite(13,LOW);
      state[2]=LOW;
 }
}

void measure_power() // sending power readings to database
{
  float current=0;
  if(state[0]==HIGH) // uri = smartsocket/IOT/insert_plug1.php / A0
  { 
      delay(10);
        current=((((analogRead(A0)/1023)*5000)-2500)/100);
        httppost("/smartsocket/IOT/insert_plug1.php","voltage=220&current="+(String)current);
     //  delay(100);
  }
   if(state[1]==HIGH)  //  uri = smartsocket/IOT/insert_plug2.php / A3
  {
      delay(10);
       current=((((analogRead(A1)/1023)*5000)-2500)/100);
       httppost("/smartsocket/IOT/insert_plug2.php","voltage=220&current="+(String)current);

  }
   if(state[2]==HIGH) //  uri = smartsocket/IOT/insert_plug3.php / A2
  {   delay(10);  
       current=((((analogRead(A2)/1023)*5000)-2500)/100);
      httppost("/smartsocket/IOT/insert_plug3.php","voltage=220&current="+(String)current);
  }
}

void setup(){
for(int i=5; i<=13;i++) //8-10 LED 11-13 relay
  {
    if(i<=7) //5-7 switch
    {
      pinMode(i,INPUT);
    }
    if((i>=8) &&(i<=13)) //8-10 LED, 11-13 relay
    {
      pinMode(i,OUTPUT);
    }
  }
 pinMode(4,INPUT); // reset switch
 pinMode(A0,INPUT); //current sensor 1
 pinMode(A1,INPUT); // current sensor 2
 pinMode(A2,INPUT); // current sensor 3
 Serial.begin(9600);
 esp.begin(9600);
//  esp.println("AT+CIPMUX=1");
  thread1->onRun(receive_data);
  thread1->setInterval(100);
   thread2->onRun(switch_socket);
  thread2->setInterval(150);
   thread3->onRun(measure_power);
  thread3->setInterval(200);
  controll.add(thread1);
  controll.add(thread2); 
    controll.add(thread3); 
}

void loop(){
  controll.run();
  float h = 3.1415;
  h/=2;
}
