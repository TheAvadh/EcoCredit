// CheckoutForm.js
import React from 'react';

const CheckoutForm = ({ shippingCosts, handleSelectShippingCost }) => {
  return (
    <div class="iphone">
  <header class="header">
    <h1>Checkout</h1>
  </header>

  <form action="" class="form" method="POST">
  <div>
      <h2>Pickup Details</h2>

      <div class="card">
        <address>
          props.pickup.time<br />
          props.pickup.day
        </address>
      </div>
    </div>

    <div>
      <h2>Address</h2>

      <div class="card">
        <address>
          props.user.name<br />
          props.user.address
        </address>
      </div>
    </div>

    <fieldset>
      <legend>Pickup Type</legend>
      {shippingCosts.map(cost => (
        <div class="form_radio" key={cost.id}>
          <p>Price: {cost.metadata.amount}$</p>
            <button
              key={cost.id}
              onClick={() => handleSelectShippingCost(cost.id)}
              disabled={cost.selected}
            >
              {cost.name}
            </button>
        </div>
      ))}

    </fieldset>

    <div>
      <button class="button button--full" type="submit">Checkout</button>
    </div>
  </form>
</div>

  );
};

export default CheckoutForm;
