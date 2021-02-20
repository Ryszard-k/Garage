import React, { Component } from 'react'
import Cars from './Cars'

class App extends Component{

state = {
  cars: []
}

componentDidMount() {
  fetch('http://localhost:8080/employees/cars')
  .then(Response => Response.json())
  .then(data => {
    this.setState({cars: data})
  });
}

render() {
  return (
    <Cars cars={this.state.cars} />
  )
}

}
export default App

