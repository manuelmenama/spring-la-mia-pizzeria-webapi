//Global variables
//localhost:8080/api/v1/pizzas
const BASE_URL = "http://localhost:8080/api/v1/pizzas";
const contentElement = document.getElementById('content');
const toggleForm = document.getElementById('toggle-form');
const pizzaForm = document.getElementById('pizza-form');

//Api
const getBookList = async() => {
  const response = await fetch(BASE_URL);
  return response;
};

const postPizza = async(jsonData) => {
  const fetchOption = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: jsonData,
  };
  const response = await fetch(BASE_URL, fetchOption);
  return response;
};

//Dom manipulation
const toggleFormVisibility = () => {
  document.getElementById('form').classList.toggle('d-none');
};

const createIngredientList = (ingredients) => {
  let ingredientWrapper = `<p>`;
  ingredients.forEach(element => {
    ingredientWrapper += `
      <span class="badge text-bg-secondary">${element.name}</span>
    `;
  });  
  ingredientWrapper += `</p>`;
  return ingredientWrapper;
};

const createPizzaCard = (item) => {
  return `
    <div class="col-4">
      <div class="card" style="width: 18rem;">
        <div class="card-header">
          <h5>
            ${item.name}
          </h5>  
        </div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item">Descrizione: ${item.description}</li>
          <li class="list-group-item">Prezzo: ${item.price}€</li>
        </ul>
        <div class="card-footer">
          ${createIngredientList(item.ingredients)}
        </div>
      </div>
    </div>
  `;
};

const createPizzaWrapper = (data) => {
  console.log(data);
  let wrapper = 
    `
      <div class="row gy-3">
        
    `;
  //aggiugo una card
  data.forEach((element) => {
    wrapper += createPizzaCard(element);
  });
  wrapper += "</div>";
  return wrapper;
};

//Other function
const loadData = async() => {
  const response = await getBookList();
  console.log(response);
  if(response.ok) {
    response.json().then((data) => {
      contentElement.innerHTML = createPizzaWrapper(data);

    });
  } else {
    console.log("Error!");
  }
};

const savePizza = async(event) => {
  //prevent default
  event.preventDefault();
  
  //read input value
  const formData = new FormData(event.target);
  const formDataObject = Object.fromEntries(formData.entries());

  //validation

  //produce a json
  const formDataJson = JSON.stringify(formDataObject);

  //send ajax request
  const response = await postPizza(formDataJson);

  //parse response
  const responseBody = await response.json();
  if(response.ok) {
    loadData();
    event.target.reset();
  } else{
    console.log("ERROR!");
    console.log(response.status);
    console.log(responseBody);
  }
};

//Global code
toggleForm.addEventListener("click", toggleFormVisibility);
pizzaForm.addEventListener("submit", savePizza);
loadData();