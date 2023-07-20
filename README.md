**POST Request-Body format:**
```tsx
{
  "email"?: string,
  "phoneNumber"?: number
}
```
**Response Body:**
```tsx
{
  "contact":{
    "primaryContatctId": number,
    "emails": string[], // first element being email of primary contact 
    "phoneNumbers": string[], // first element being phoneNumber of primary contact
    "secondaryContactIds": number[] // Array of all Contact IDs that are "secondary" to the primary contact
  }
}
```
If an incoming request has either of `phoneNumber` or `email` common to an existing contact but contains new information, the service will create a “secondary” `Contact` row.

**Example:**

**Existing state of database:**

```jsx
{
  id                   1                   
  phoneNumber          "123456"
  email                "lorraine@hillvalley.edu"
  linkedId             null
  linkPrecedence       "primary"
  createdAt            2023-04-01 00:00:00.374+00              
  updatedAt            2023-04-01 00:00:00.374+00              
  deletedAt            null
}
```

******************************Identify request:******************************
```jsx
{
"email":"mcfly@hillvalley.edu",
"phoneNumber":"123456"
}
```

New state of database:
```jsx
{
  id                   1                   
  phoneNumber          "123456"
  email                "lorraine@hillvalley.edu"
  linkedId             null
  linkPrecedence       "primary"
  createdAt            2023-04-01 00:00:00.374+00              
  updatedAt            2023-04-01 00:00:00.374+00              
  deletedAt            null
},
{
  id                   23                   
  phoneNumber          "123456"
  email                "mcfly@hillvalley.edu"
  linkedId             1
  linkPrecedence       "secondary"
  createdAt            2023-04-20 05:30:00.11+00              
  updatedAt            2023-04-20 05:30:00.11+00              
  deletedAt            null
},
```
### Can primary contacts turn into secondary?

Yes. Let’s take an example

**Existing state of database:**
```
{
  id                   11                   
  phoneNumber          "919191"
  email                "george@hillvalley.edu"
  linkedId             null
  linkPrecedence       "primary"
  createdAt            2023-04-11 00:00:00.374+00              
  updatedAt            2023-04-11 00:00:00.374+00              
  deletedAt            null
},
{
  id                   27                   
  phoneNumber          "717171"
  email                "biffsucks@hillvalley.edu"
  linkedId             null
  linkPrecedence       "primary"
  createdAt            2023-04-21 05:30:00.11+00              
  updatedAt            2023-04-21 05:30:00.11+00              
  deletedAt            null
}
```

**Request:**
```
{
  "email":"george@hillvalley.edu",
  "phoneNumber": "717171"
}
```
**Response:**
```
{
  "contact":{
    "primaryContatctId": 11,
    "emails": ["george@hillvalley.edu","biffsucks@hillvalley.edu"]
    "phoneNumbers": ["919191","717171"]
    "secondaryContactIds": [27]
  }
}
```
