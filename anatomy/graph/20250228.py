import matplotlib.pyplot as plt
from mpl_toolkits.basemap import Basemap

# Define key locations with coordinates
locations = {
    "White House, USA": (38.8977, -77.0365),
    "Ukraine": (48.3794, 31.1656),
    "Russia": (55.7512, 37.6184),
    "Bakhmut": (48.5956, 38.0009),
    "Pokrovsk": (48.2828, 37.1758),
    "Kurakhove": (47.9852, 37.2875),
    "Kharkiv": (50.0040, 36.2315),
    "Germany": (51.1657, 10.4515),
    "United Kingdom": (51.5074, -0.1278),
    "Poland": (51.9194, 19.1451),
    "France": (48.8566, 2.3522)
}

# Create map
fig, ax = plt.subplots(figsize=(12, 6))
m = Basemap(projection="merc", llcrnrlat=30, urcrnrlat=60, llcrnrlon=-30, urcrnrlon=50, ax=ax)
m.drawcoastlines()
m.drawcountries()
m.fillcontinents(color='lightgray', alpha=0.3)

# Plot alliances
allied_countries = ["White House, USA", "Ukraine", "Germany", "United Kingdom", "Poland", "France"]
russian_advances = ["Bakhmut", "Pokrovsk", "Kurakhove", "Kharkiv"]

for loc, coord in locations.items():
    x, y = m(coord[1], coord[0])  # Convert to map coordinates
    if loc in allied_countries:
        m.scatter(x, y, color='blue', s=100, label="Ukraine & Allies" if loc == "White House, USA" else "")
    elif loc in russian_advances:
        m.scatter(x, y, color='red', s=100, label="Russian Advances" if loc == "Bakhmut" else "")
    else:
        m.scatter(x, y, color='black', s=100, label="Russia" if loc == "Russia" else "")
    plt.text(x, y, loc, fontsize=10, ha="right", color="black")

# Add troop movements (arrows)
movement_arrows = [
    ("Russia", "Ukraine"),
    ("Ukraine", "Bakhmut"),
    ("Bakhmut", "Pokrovsk"),
    ("Pokrovsk", "Kurakhove"),
    ("Kurakhove", "Kharkiv"),
]

for start, end in movement_arrows:
    x1, y1 = m(locations[start][1], locations[start][0])
    x2, y2 = m(locations[end][1], locations[end][0])
    # Use plt.arrow() for arrows
    plt.arrow(x1, y1, x2 - x1, y2 - y1, color="red", width=0.05, head_width=0.15, head_length=0.3)

plt.legend()
plt.title("Ukraine War Events and Alliances (2022-2025)")
plt.show()

