import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Dashboard() {
    const [events, setEvents] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEvents = async () => {
            const token = localStorage.getItem("token");
            const response = await axios.get("http://localhost:8080/api/events", {
                headers: { Authorization: `Bearer ${token}` },
            });
            setEvents(response.data);
        };

        fetchEvents();
    }, []);

    return (
        <div>
            <h2>Upcoming Events</h2>
            <ul>
                {events.map((event) => (
                    <li key={event.id} onClick={() => navigate(`/events/${event.id}`)}>
                        {event.name} - {new Date(event.dateTime).toLocaleString()}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Dashboard;
