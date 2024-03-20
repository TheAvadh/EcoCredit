import Dashboard from "../../../components/Dashboard";
import {
    faCalendarCheck
  } from "@fortawesome/free-solid-svg-icons";
import ScheduledPickups from "../Pickup/ScheduledPickups";

const AdminDashboard = () => {
    const tabs = [
        {
            label: "Scheduled Pickups",
            to: "/admin/scheduled-pickups",
            path: "/scheduled-pickups",
            icon: faCalendarCheck,
            component: <ScheduledPickups />,
          }
    ];

    return <Dashboard tabs={tabs} />;
};

export default AdminDashboard;