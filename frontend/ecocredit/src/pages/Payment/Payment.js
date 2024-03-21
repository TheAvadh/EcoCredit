import React, { useState, useEffect } from 'react';
import Cookies from "js-cookie";
import { Card, Button } from 'react-bootstrap';
import "./Payment.css";

const ShippingCostsComponent = (props) => {
  const [shippingCost, setShippingCost] = useState(null);

  useEffect(() => {
    async function fetchCheckout() {
      try {
        const response = await fetch(`${process.env.REACT_APP_BASE_URL}/checkout`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        });
        const data = await response.json();

        if (response.ok) {
          setShippingCost(data); // Set the shipping cost directly
        } else {
          console.error("Error fetching checkout:", data);
        }
      } catch (error) {
        console.error("Error fetching checkout:", error);
      }
    }

    fetchCheckout();
  }, []);

  const handleCheckout = async () => {
    if (shippingCost) {
      try {
        const response = await fetch(`${process.env.REACT_APP_BASE_URL}/checkout/charge`, {
          method: 'POST',
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${Cookies.get("token")}`,
          },
          body: JSON.stringify({
            defaultPrice: shippingCost.defaultPrice
          })
        });
  
        if (response.ok) {
          const data = await response.json();

          if (data.checkoutUrl) {
            window.location.href = data.checkoutUrl;
          } else {
            console.error("URL not found in API response:", data);
          }
        } else {
          console.error("Error charging for checkout:", response.statusText);
        }
      } catch (error) {
        console.error("Error charging for checkout:", error);
      }
    } else {
      console.error("Shipping cost data not available.");
    }
  };

  return (
    <div className="shipping-container">
      <div className="pickup-schedule">
        <h2>Pickup Schedule</h2>
        <p>Pickup Schedule Time: {props.day} - {props.date} - {props.time}</p>
      </div>
      <div className="shipping-costs">
        <h2>Shipping Cost</h2>
        <div className="shipping-card-container">
          {shippingCost && (
            <Card className="shipping-card">
              <Card.Body>
                <Card.Title>{shippingCost.name}</Card.Title>
                <Card.Text>{shippingCost.metadata.amount}$</Card.Text>
              </Card.Body>
            </Card>
          )}
        </div>
      </div>
      <div className="checkout-section">
        <button className="checkout-button" onClick={handleCheckout}>Checkout</button>
      </div>
    </div>
  );
};

export default ShippingCostsComponent;
