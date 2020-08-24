import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

class R5ProdMasVendidos extends React.Component {
  state = {
    datos: [],
  };

  async componentDidMount() {
    await this.fetchEmpleadoApp();
  }
  fetchEmpleadoApp = async () => {
    const tokenBody = {
      password: 'admin',
      username: 'admin',
    };

    const tokenRequest = {
      method: 'POST',
      body: JSON.stringify(tokenBody),
      headers: {
        'Content-Type': 'application/json',
      },
    };

    const tokenResponse = await fetch('http://localhost:8080/api/authenticate', tokenRequest);
    const tokenJson = await tokenResponse.json();

    const res = await fetch('http://localhost:9000/api/sales/5ProductosConMasIngresos', {
      method: 'get',
      headers: new Headers({
        Authorization: 'Bearer ' + tokenJson.id_token,
        'Content-Type': 'application/x-www-form-urlencoded',
      }),
    });
    // const datos = await res.json()

    // !  Funcion para ordenar Json
    function sortJSON(key, data, orden) {
      return data.sort(function (a, b) {
        const x = a[key],
          y = b[key];
        if (orden === 'asc') {
          return x < y ? -1 : x > y ? 1 : 0;
        }

        if (orden === 'desc') {
          return x > y ? -1 : x < y ? 1 : 0;
        }
      });
    }


    const aux = sortJSON('precio', await res.json(), 'desc');
    const datos = aux.slice(0,5);

    this.setState({
      datos,
    });
  };

  render() {
    return (
      <BarChart width={600} height={300} data={this.state.datos} margin={{ top: 5, right: 125, left: 20, bottom: 5 }}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="producto" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="precio" fill="#8884d8" />
      </BarChart>
    );
  }
}

export default R5ProdMasVendidos;
