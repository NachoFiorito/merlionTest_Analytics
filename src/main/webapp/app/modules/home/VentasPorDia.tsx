import React from 'react';
import {LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend,} from 'recharts';

class VentasPorDia extends React.Component  {

    state = {
      datos: []
    }
  
    async componentDidMount(){
      await this.fetchEmpleadoApp()
    }
      fetchEmpleadoApp = async () => {

        const tokenBody= {
          password: "admin",
          username: "admin"
        }
        
        const tokenRequest = {
          method: 'POST',
          body: JSON.stringify(tokenBody),
          headers: {
              'Content-Type': 'application/json'
          }
        };
        
        const tokenResponse = await fetch("http://localhost:8080/api/authenticate", tokenRequest);
        const tokenJson = await tokenResponse.json();

        const res = await fetch('http://localhost:9000/api/sales/VentasPorDia',{
          method:'get',
          headers: new Headers({
            'Authorization': 'Bearer '+tokenJson.id_token, 
            'Content-Type': 'application/x-www-form-urlencoded'
            })
        });
        const datos = await res.json()
    
        this.setState({
          datos
        })
    
      }
  
      render(){
    return (
        <LineChart
            width={500}
            height={300}
            data={this.state.datos}
            margin={{ 
              top: 5, right: 30, left: 20, bottom: 5,
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="fecha" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="cantVentas" stroke="#8884d8" activeDot={{ r: 8 }} />
          </LineChart>
    );
  }
};

export default VentasPorDia;