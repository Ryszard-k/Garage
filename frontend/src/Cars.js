import React, { Component } from 'react'

  const Cars = ({ cars }) => {
    return (
      <div>
        {cars.map((car) => (
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">{car.brand}</h5>
              <h6 class="card-subtitle mb-2 text-muted">{car.model}</h6>
              <p class="card-text">{car.cost}</p>
              <p class="card-text">{car.manufactureYear}</p>
            </div>
          </div>
        ))}
      </div>
    )
  };

  export default Cars

