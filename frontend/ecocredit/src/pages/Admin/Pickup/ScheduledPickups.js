import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";

const ScheduledPickups = () => {
    const [pickups, setPickups] = useState([
        {
            "id": 1,
            "date": "2024-03-19",
            "time": "12:30",
            "userId": 21,
            "status": "SCHEDULED",
            "wastes": [
                {
                    "wasteId": 1,
                    "weight": 3.0,
                    "category": "biodegradable"
                },
                {
                    "wasteId": 2,
                    "weight": 3.0,
                    "category": "plastics"
                }
            ]
        },
        {
            "id": 2,
            "date": "2024-03-18",
            "time": "21:47",
            "userId": 21,
            "status": "SCHEDULED",
            "wastes": [
                {
                    "wasteId": 105,
                    "weight": null,
                    "category": "biodegradable"
                }
            ]
        }
    ]);

return (
    <Container fluid className="background-image">
      <Row className="d-flex justify-content-center align-items-center">
        <Col lg={8} className="mt-5">
          <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
            <h1 className="text-center text-ec-dark-green p-2">
              Scheduled Pickups
            </h1>
            <Table
              striped
              bordered
              hover
              variant="ec-grey"
              className="table-custom mb-0"
            >
              <thead>
                <tr>
                  <th>#</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>UserID</th>
                  <th>Status</th>
                  <th>Weights</th>
                </tr>
              </thead>
              <tbody>
                {pickups.map((pickup, index) => (
                  <tr key={pickup.id}>
                    <td>{index + 1}</td>
                    <td>{pickup.date}</td>
                    <td>{pickup.time}</td>
                    <td>{pickup.userId}</td>
                    <td>{pickup.status}</td>
                    <Table striped
                            bordered
                            hover
                            variant="ec-grey"
                            className="table-custom mb-0">
                                <tbody>
                                    <tr>
                                        <td>item1</td>
                                        <td>weight1</td>
                                        <td>category1</td>
                                    </tr>
                                    <tr>
                                        <td>item2</td>
                                        <td>weight2</td>
                                        <td>category2</td>
                                    </tr>
                                </tbody>
                    </Table>
                  </tr>
                )
                )}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
    </Container>
    );
};


export default ScheduledPickups;