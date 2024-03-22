import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import "../Styling/Bids.css";

const ViewBids = () => {
    // dummy data
    const [bids] = useState([
        {
            "waste": {
                "id": 6,
                "category": {
                    "id": 4,
                    "value": "plastics"
                },
                "weight": 1.0
            },
            "base_price": 2.0736,
            "top_bid_amount": 2.0736,
            "user": {
                "firstName": "Jane",
                "lastName": "Doe"
            },
            "date": "2024-03-20T22:39:00",
            "sold": true,
            "id": 9,
            "_active": false
        },
        {
            "waste": {
                "id": 7,
                "category": {
                    "id": 4,
                    "value": "plastics"
                },
                "weight": 2.0
            },
            "base_price": 2.0736,
            "top_bid_amount": 2.0736,
            "user": null,
            "date": "2024-03-21T22:39:00",
            "sold": true,
            "id": 9,
            "_active": false
        
        }
    ]);

   return (
        <Container fluid className="background-image">
            <Row className="d-flex justify-content-center align-items-center">
                <Col lg={12} className="mt-5">
                    <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
                        <h1 className="text-center text-ec-dark-green p-2">
                            View Bids
                        </h1>
                        <div className="bids-table-wrapper">
                            <Table
                            bordered
                            hover
                            variant="ec-grey"
                            className="table-custom mb-0"
                            >
                                <thead>
                                    <tr>
                                        <th>Waste ID</th>
                                        <th>Category</th>
                                        <th>Weight (kg)</th>
                                        <th>Base Price ($)</th>
                                        <th>Top Bid Amount ($)</th>
                                        <th>Winner</th>
                                        <th>DateTime</th>
                                        <th>Bid Status</th>
                                        <th>Payment Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {bids.map((bid) => (
                                        <tr key={bid.id}>
                                            <td>{bid.waste.id}</td>
                                            <td>{bid.waste.category.value}</td>
                                            <td>{bid.waste.weight}</td>
                                            <td>{bid.base_price}</td>
                                            <td>{bid.top_bid_amount}</td>
                                            <td>{bid.user ? bid.user.firstName + " " + bid.user.lastName : "TBD"}</td>
                                            <td>{bid.date}</td>
                                            <td>{bid._active ? "active": "inactive"}</td>
                                            <td>{bid.sold ? "paid" : "not paid"}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </Table>
                        </div>
                    </div>
                </Col>
            </Row>
        </Container>
   );
};


export default ViewBids;