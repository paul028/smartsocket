  #include <SoftwareSerial.h>
SoftwareSerial esp(2,3);
String ssid ="ASUS";
String password="May131997";

String data;
String server= "192.168.1.2";
String uri ="/insert_all.php";
boolean state[]={LOW,LOW,LOW}; 
void setup() 
{
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
 Serial.begin(115200);
 esp.begin(115200);
}


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
} delay(1000);
String postRequest =
"POST " + uri + " HTTP/1.0\r\n" +
"Host: " + server + "\r\n" +
"Accept: *" + "/" + "*\r\n" +
"Content-Length: " + data.length() + "\r\n" +
"Content-Type: application/x-www-form-urlencoded\r\n" +
"\r\n" + data;
String sendCmd = "AT+CIPSEND=";//determine the number of caracters to be sent.
Serial.println(postRequest);
esp.print(sendCmd);
esp.println(postRequest.length() );
delay(500);
if(esp.find(">"))
{ Serial.println("Sending.."); esp.print(postRequest);
if( esp.find("SEND OK")) 
{ Serial.println("Packet sent");
while (esp.available()) {
String tmpResp = esp.readString();
Serial.println(tmpResp);
}// close the connection
esp.println("AT+CIPCLOSE");
}
}}
void receive_data()
{
  
}

void switch_socket() 
{
  short get_switch=0;
   if((digitalRead(5)==1)||(get_switch==1))
 {
    state[0] =!state[0]; 
    delay(1000);
    digitalWrite(8,state[0]);
    digitalWrite(11,state[0]);
 }
   if((digitalRead(6)==1)||(get_switch==1))
 {
    state[1] =!state[1]; 
    delay(1000);
    digitalWrite(9,state[1]);
    digitalWrite(12,state[1]);
 }
   if((digitalRead(7)==1)||(get_switch==1))
 {
    state[2] =!state[2]; 
    delay(1000);
    digitalWrite(10,state[2]);
    digitalWrite(13,state[2]);
 }
}

void measure_power()
{
  if(state[0]==HIGH) // uri = smartsocket/insert_plug1.php
  {
  }
   if(state[0]==HIGH)  // uri = smartsocket/insert_plug2.php
  {
  }
   if(state[0]==HIGH) // uri = smartsocket/insert_plug3.php
  {
    
  }
}

void loop() 
{
  switch_socket();
}
