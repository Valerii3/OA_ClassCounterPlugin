# OA_ClassCounterPlugin
---
# Motivation and Experience

I will shorly introduce myself:
 * [CV](https://drive.google.com/file/d/1d_Z4X_zg25DYx57H-k_z-_uYixXS-qpy/view?usp=sharing)
 * [My summer internship at JetBrains](https://www.linkedin.com/feed/update/urn:li:activity:7108471542686068737/)

During this **summer** I was writing a plugin for JetBrains WebTeamUI where I connected `js/tsx` components with their documenation pages. This experience allowed me to dive deep into the technologies like `PSI` structure and `JCEF` browser, improving my skills in `Kotlin`.

I worked a lot with our frontend / backend developers, designers and my mentor. I liked a lot to communicate and solve together our big task. This is why I am also **interested** in creating a `IDE Plugin Development Course`, because in description it was mentioned: *active collaboration with the JetBrains Academy team*.

# Implementation

For this project, a plugin was developed to count the number of classes and methods in `Java` files within an IntelliJ project, updating these counts in `real-time`. 

Utilizing `Swing`, a user interface displays the counts and, when a file is selected, shows additional information including the:
* file's name
* visibility
* annotations

The plugin's accuracy and performance are validated through `JUnit` tests, ensuring reliable operation.

![unable to load image](https://drive.google.com/uc?export=download&id=1N5Wf8--7y88AlSpvexRwaY-Ewtv3__-E)



# Installation (manually)

1.Install Git;

2.Clone the repo using `ssh`: `git clone git@github.com:Valerii3/OA_WebTeamUI.git`

3.Open the basic_plugin directory in a console 

4.Build the plugin: `gradlew buildPlugin`

5.Run the plugin: `gradlew runIde`

# Installation (automatic)

1. Clone the repo using `ssh`: `git clone git@github.com:Valerii3/OA_WebTeamUI.git`
2. Open basic_plugin directory as **IntelliJ IDEA** project
3. Run using `gradle` (argument: `runIde`)
4. Test the plugin clicking `run test` in  MyPluginTest file.


# Regards
**Thank you JetBrains for the opportunity to become a part of WEBTeamUI during the internship**
