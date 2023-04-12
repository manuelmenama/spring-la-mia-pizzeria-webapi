//Global variables
//localhost:8080/api/v1/pizzas
const BASE_URL = "http://localhost:8080/api/v1/pizzas";
const contentElement = document.getElementById('content');

//Api
const getBookList = async() => {
  const response = await fetch(BASE_URL);
  return response;
}

//Dom manipulation

//Other function
const loadData = async() => {
  const response = await getBookList();
  console.log(response.json());
}

//Global code
loadData();