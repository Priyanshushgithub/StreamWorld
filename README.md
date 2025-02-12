# StreamWorld
# Project Overview
StreamWorld is an Android application designed to fetch and display information about movies and TV shows using the WatchMode API. The app gives users a toggle option to switch between movies and TV series, a homepage listing all available titles, and a detailed view for each title, showcasing essential details like the poster, description, and release year.

# Features Implemented
# Home Page
Dynamic Data Fetching: Fetches movies or TV series data dynamically from the WatchMode API based on the user's selection.
List View: Displays a list of titles in a RecyclerView for seamless scrolling and user interaction.
Toggle Button: Allows users to switch between "Movies" and "TV Shows" categories.

# Details Page
Poster Display: Shows the poster of the selected movie/TV show.
Release Year and Description: Displays the release year and description fetched via a specific API endpoint for detailed title information.
Back Navigation: Includes a back button for smooth navigation back to the home screen.

# Additional Features
Edge-to-Edge UI: Implements modern UI designs with edge-to-edge layouts.
Shimmer Effect: Displays a shimmer loading effect while data is being fetched from the API.
Error Handling: Includes Toast messages to notify users of errors during API requests.

# Challenges Faced
# 1. Glide Image Loading Issues:
Faced difficulties with images not loading correctly due to URL or context mismatches.
Resolved by ensuring correct URL parsing and proper Glide context usage.

# 2. Handling Empty or Missing Data:
Encountered situations where API responses had missing fields (e.g., release date, description).
Resolved by using fallback values like "Unknown" or "No description available."

# 3.RecyclerView Adapter Implementation:
Initially, I faced issues with unresolved references for custom fields in the Movie class.
Fixed by creating a proper data model for the Movie and ensuring type safety.

# 4.Navigating API Design:
The API structure required title-specific details via another endpoint.
Managed by making separate API calls and updating the UI accordingly.

# 5. Back Button Behavior:
Ensuring the back button exited the app gracefully without unexpected behaviour.
Resolved by overriding the onBackPressed method and implementing a confirmation mechanism.
