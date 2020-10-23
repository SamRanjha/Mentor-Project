package main

import (
	"context"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type Payments struct {
	Id       string `json:"Id"`
	Uid      string `json:"Uid"`
	Mid      string `json:"MId"`
	Tid      string `json:"Tid"`
	Amt      string `json:"Amt"`
	Datetime string `json:"Datetime"`
	Remarks  string `json:"Remarks"`
}

var collection *mongo.Collection

func init() {
	// connection to database
	fmt.Println("Connecting to mongo")
	clientOptions := options.Client().ApplyURI("mongodb://127.0.0.1:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		fmt.Println("error")
		log.Fatal(err)
	}
	fmt.Println("Connected to MongoDB!")
	collection = client.Database("payments").Collection("payments")

}

func main() {
	myRouter := mux.NewRouter().StrictSlash(true)
	myRouter.HandleFunc("/createPayment", createPayment).Methods("POST")
	myRouter.HandleFunc("/getPayment/{id}", getPayment).Methods("GET")
	myRouter.HandleFunc("/user/getPayment/{id}", getPaymentUser).Methods("GET")
	myRouter.HandleFunc("/mentor/getPayment/{id}", getPaymentUser).Methods("GET")
	myRouter.HandleFunc("/updatePayment", updatePayment).Methods("PUT")
	http.ListenAndServe(":9016", myRouter)

}

func createPayment(w http.ResponseWriter, req *http.Request) {
	fmt.Println("in createPayment")
	reqbody, _ := ioutil.ReadAll(req.Body)
	var payment Payments
	json.Unmarshal(reqbody, &payment)
	insertResult, err := collection.InsertOne(context.TODO(), payment)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Inserted a Single Document: ", insertResult.InsertedID)
	fmt.Println("Received object: ", payment)
}

func getPayment(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idval := vars["id"]
	fmt.Println(idval)
	filter := bson.M{"id": idval}
	var result Payments
	err := collection.FindOne(context.TODO(), filter).Decode(&result)
	if err != nil {
		log.Fatal(err)
	}
	json.NewEncoder(w).Encode(result)
}

func getPaymentUser(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idval := vars["id"]
	fmt.Println(idval)
	findOptions := options.Find()
	filter := bson.M{"uid": idval}
	var results []Payments
	cur, err := collection.Find(context.TODO(), filter, findOptions)
	if err != nil {
		log.Fatal(err)
	}
	for cur.Next(context.TODO()) {
		var elem Payments
		err := cur.Decode(&elem)
		if err != nil {
			log.Fatal(err)
		}
		fmt.Println(elem)
		results = append(results, elem)
	}
	json.NewEncoder(w).Encode(results)
}

func getPaymentMentor(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idval := vars["id"]
	fmt.Println(idval)
	findOptions := options.Find()
	filter := bson.M{"mid": idval}
	var results []Payments
	cur, err := collection.Find(context.TODO(), filter, findOptions)
	if err != nil {
		log.Fatal(err)
	}
	for cur.Next(context.TODO()) {
		var elem Payments
		err := cur.Decode(&elem)
		if err != nil {
			log.Fatal(err)
		}
		fmt.Println(elem)
		results = append(results, elem)
	}
	json.NewEncoder(w).Encode(results)
}

type update struct{
	Id       string `json:"Id"`
	Amt      string `json:"Amt"`
}

func updatePayment(w http.ResponseWriter, req *http.Request) {
	reqbody, _ := ioutil.ReadAll(req.Body)
	var request update
	json.Unmarshal(reqbody, &request)

	idval := request.Id
	amt := request.Amt

	fmt.Println(idval," ",amt)
	findOptions :=  bson.D{
        {"$set", bson.D{{"amt", amt}}},
    }
	filter := bson.M{"id": idval}
	_,err := collection.UpdateOne(context.TODO(), filter, findOptions)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Fprintf(w,"Successfully updated amount")
}
