11111112222222222223333333333333333ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
      <td>삭제</td>
    </tr>
    <tr th:each="dto: ${dto}">
        <td>
            <input type="checkbox" name="check">
        </td>
        <td>
            <img th:src="@{|/thumb/Thumb_${dto.item.itemFileEntities.get(0).StoredFileName}|}" style="width:150px; height:150px;">
        </td>
        <td th:text="${dto.item.name}"></td>
        <td>
            <button onclick="plus()">+</button>
            <span  id="quantity" th:text="${dto.quantity}"></span>
            <button onclick="minus()">-</button>
        </td>

        <td th:text="${#numbers.formatInteger(dto.item.price,0,'COMMA')}+'원'"></td>
        <td>
            <button onclick="deleteReq()">삭제</button>
        </td>
    </tr>
</table>

