import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import Cookies from "js-cookie";
import biodegradableURL from "../../assets/images/biodegradable.jpg";
import electronicsURL from "../../assets/images/electronics.jpg";
import glassURL from "../../assets/images/glass.jpg";
import mixedURL from "../../assets/images/mixed.jpg";
import paperURL from "../../assets/images/paper.jpg";
import plasticsURL from "../../assets/images/plastics.jpg";

const imageMap = {
  biodegradable: biodegradableURL,
  electronics: electronicsURL,
  glass: glassURL,
  mixed: mixedURL,
  paper: paperURL,
  plastics: plasticsURL,
};

const ActiveBids = () => {
  const navigate = useNavigate();

  const [bids, setBids] = useState([]);

  useEffect(() => {
    fetch(`${process.env.REACT_APP_BASE_URL}/recycler/active-bids`, {
      method: "GET",
      mode: "cors",
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        const parsedBids = data
          .map((bid) => ({
            ...bid,
            date: moment(bid.date, "YYYYMMDDHHmm").toISOString(),
          }))
          .sort((a, b) => moment(a.date).unix() - moment(b.date).unix());
        setBids(parsedBids);
      })
      .catch((error) =>
        console.error("Error fetching active bids data failed:", error)
      );
  }, []);

  const calculateTimeLeft = (startDate) => {
    const end = moment(startDate).add(24, "hours");
    const now = moment();

    if (now.isAfter(end)) {
      return "First bidder wins";
    } else {
      const duration = moment.duration(end.diff(now));
      return `Time Left: ${duration.hours()}hr ${duration.minutes()}min`;
    }
  };

  return (
    <Container fluid className="background-image">
      <Row xs={1} sm={2} lg={3} className="p-4">
        {bids.map((bid) => (
          <Col key={bid.id} className="g-3">
            <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
              <Card.Img variant="top" src={imageMap[bid.waste_type]} />
              <Card.Body>
                <Card.Title className="fs-3 fw-bold">
                  {bid.waste_type.charAt(0).toUpperCase() +
                    bid.waste_type.slice(1)}
                </Card.Title>
                <div>
                  <dl className="row">
                    <dt className="col-sm-5">Weight</dt>
                    <dd className="col-sm-7">{bid.waste_weight} KG</dd>

                    <dt className="col-sm-5">Current Highest Bid</dt>
                    <dd className="col-sm-7">{bid.highest_bid} CAD</dd>

                    <dt className="col-sm-5">Starting Bid</dt>
                    <dd className="col-sm-7">{bid.bid_amount} CAD</dd>
                  </dl>
                  <div className="text-end">
                    <Button
                      variant="ec-dark-green"
                      onClick={() =>
                        navigate(`/recycler/active-bids/${bid.id}`)
                      }
                    >
                      Place a Bid
                    </Button>
                  </div>
                </div>
              </Card.Body>
              <Card.Footer>
                <small className="text-muted">
                  Created On: {moment(bid.date).format("LLL")}
                  <br />
                  <div className="fs-6 fw-bold">
                    {calculateTimeLeft(bid.date)}
                  </div>
                </small>
              </Card.Footer>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default ActiveBids;
