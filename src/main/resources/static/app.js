// Function to fetch and display teachers from the Spring Boot API
function loadTeachers() {
    fetch('/api/teachers')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('teacherTableBody');
            tableBody.innerHTML = ''; // Clear the table first

            data.forEach(teacher => {
                const row = `<tr>
                                <td>${teacher.id}</td>
                                <td>${teacher.name}</td>
                                <td>${teacher.department}</td>
                                <td>
                                    <button class="btn btn-warning btn-sm me-2" 
                                            onclick="updateDepartment(${teacher.id}, '${teacher.name}')">Edit Dept</button>
                                            
                                    <button class="btn btn-danger btn-sm" 
                                            onclick="deleteTeacher(${teacher.id})">Delete</button>
                                </td>
                             </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error fetching teachers:', error));
}

// Function to update a teacher's department
function updateDepartment(id, name) {
    // Pop up a box asking the user for the new department
    const newDept = prompt(`Enter a new department for ${name}:`);

    // If they typed something and clicked OK, send the PUT request
    if (newDept) {
        fetch(`/api/teachers/${id}?department=${encodeURIComponent(newDept)}`, { method: 'PUT' })
            .then(() => loadTeachers()) // Reload the table to show the change!
            .catch(error => console.error('Error updating teacher:', error));
    }
}

// Function to delete a teacher
function deleteTeacher(id) {
    if(confirm("Are you sure you want to delete this teacher?")) {
        fetch(`/api/teachers/${id}`, { method: 'DELETE' })
            .then(() => loadTeachers()) // Reload the table after deleting!
            .catch(error => console.error('Error deleting teacher:', error));
    }
}

// Wait for the HTML webpage to fully load, then fetch the teachers
document.addEventListener('DOMContentLoaded', loadTeachers);

// Function to handle adding a new teacher
document.getElementById('addTeacherForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Stop the form from refreshing the whole webpage

    // Grab the values the user typed in
    const nameValue = document.getElementById('nameInput').value;
    const deptValue = document.getElementById('deptInput').value;

    // Package them into a JSON object
    const newTeacher = {
        name: nameValue,
        department: deptValue
    };

    // Send the POST request to the Spring Boot Waiter
    fetch('/api/teachers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Tell the Waiter to expect JSON
        },
        body: JSON.stringify(newTeacher) // Convert our object to a JSON string
    })
        .then(response => response.json())
        .then(() => {
            // 1. Clear the form boxes so they are empty again
            document.getElementById('nameInput').value = '';
            document.getElementById('deptInput').value = '';

            // 2. Reload the table so the new teacher shows up instantly!
            loadTeachers();
        })
        .catch(error => console.error('Error adding teacher:', error));
});