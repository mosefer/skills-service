package skills.icons

import skills.icons.CssGenerator
import spock.lang.Specification
import skills.storage.model.CustomIcon

class CssGeneratorSpec extends Specification {

    def "generates css"(){

        String mockDataUri1 = "data:image/png;base64:rarararararararararachacha"
        String mockDataUri2 = "data:image/png;base64:rararararachacha"

        CustomIcon icon1 = new CustomIcon(projectId: "proj", width: 48, height: 48, dataUri: mockDataUri1, filename: "file.png", id:  1)
        CustomIcon icon2 = new CustomIcon(projectId: "proj", width: 48, height: 48, dataUri: mockDataUri2, filename: "file2.jpg", id:  2)

        def icons = [icon1, icon2]

        when:
        String css = new CssGenerator().cssify(icons)

        then:
        css == '''.proj-filepng {\tbackground-image: url("''' + mockDataUri1 + '''");\tbackground-repeat: no-repeat;\twidth: 48px;\theight: 48px;\tdisplay: inline-block;}.proj-file2jpg {\tbackground-image: url("''' + mockDataUri2 + '''");\tbackground-repeat: no-repeat;\twidth: 48px;\theight: 48px;\tdisplay: inline-block;}'''
    }

    def "generates css no host"(){

        String mockDataUri1 = "data:image/png;base64:rarararararararararachacha"
        String mockDataUri2 = "data:image/png;base64:rararararachacha"

        CustomIcon icon1 = new CustomIcon(projectId: "proj", width: 48, height: 48, dataUri: mockDataUri1, filename: "file.png", id:  1)
        CustomIcon icon2 = new CustomIcon(projectId: "proj", width: 48, height: 48, dataUri: mockDataUri2, filename: "file2.jpg", id:  2)

        def icons = [icon1, icon2]

        when:
        String css = new CssGenerator().cssify(icons)

        then:
        css == '''.proj-filepng {\tbackground-image: url("''' + mockDataUri1 + '''");\tbackground-repeat: no-repeat;\twidth: 48px;\theight: 48px;\tdisplay: inline-block;}.proj-file2jpg {\tbackground-image: url("''' + mockDataUri2 + '''");\tbackground-repeat: no-repeat;\twidth: 48px;\theight: 48px;\tdisplay: inline-block;}'''
    }

    def "generates empty css when empty icons"(){

        def icons = []

        when:
        String css = new CssGenerator().cssify(icons)

        then:
        css == ""
    }


}