//Global variables
//localhost:8080/api/v1/pizzas
const BASE_URL = "http://localhost:8080/api/v1/pizzas";
const contentElement = document.getElementById('content');

//Api
const getBookList = async() => {
  const response = await fetch(BASE_URL);
  return response;
};

//Dom manipulation
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

//Global code
loadData();